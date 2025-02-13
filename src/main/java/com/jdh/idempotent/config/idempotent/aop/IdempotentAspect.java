package com.jdh.idempotent.config.idempotent.aop;

import com.jdh.idempotent.config.idempotent.annotaion.Idempotent;
import com.jdh.idempotent.config.idempotent.exception.ConflictException;
import com.jdh.idempotent.config.idempotent.exception.UnprocessableEntityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 중복된 요청을 방지하기 위해 Redis를 활용한 멱등성 처리를 담당하는 AOP 클래스
 * HTTP 요청의 중복 여부를 Redis를 통해 확인하고, 중복된 요청일 경우 예외 발생
 *
 * <p>
 * `@Idempotent` 어노테이션이 붙은 경우 동작
 * </p>
 *
 * <p>
 * 관련 예외:
 * <ul>
 *     <li>UnprocessableEntityException: 기존 요청과 다른 데이터로 중복된 요청이 들어온 경우</li>
 *     <li>ConflictException: 동일한 데이터로 중복된 요청이 들어온 경우</li>
 * </ul>
 * </p>
 *
 * <p>
 * 사용 예시:
 *
 * <pre>
 * &#64;Idempotent(expireTime = 60)
 * public ResponseEntity&lt;T&gt; processRequest() {
 *     // 로직 처리
 * }
 * </pre>
 *
 * Redis 캐시 만료 시간은 어노테이션의 `expireTime` 속성으로 설정 (default 7초)
 * </p>
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class IdempotentAspect {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * `@Idempotent` 어노테이션이 선언된 메소드를 포인트컷으로 설정
     *
     * @param idempotent 멱등성 처리를 위한 어노테이션
     */
    @Pointcut("@annotation(idempotent)")
    public void pointCut(Idempotent idempotent) {
    }

    /**
     * <p>
     * 중복된 요청인지 확인하는 로직을 실행하기 전에 실행
     * </p>
     * <p>
     * HTTP 요청의 헤더와 본문(또는 파라미터)을 Redis에 캐싱하고, 중복된 요청인 경우 예외를 발생
     * </p>
     *
     * @param joinPoint 현재 실행 중인 메서드에 대한 정보
     * @param idempotent 멱등성 처리를 위한 어노테이션 정보
     * @throws IOException 요청 본문을 읽을 수 없을 경우 발생
     */
    @Before(value = "pointCut(idempotent)", argNames = "joinPoint,idempotent")
    public void before(JoinPoint joinPoint, Idempotent idempotent) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // header 에 담겨있는 요청키 조회
        String requestKey = getRequestKey(request);
        // 요청 payload 조회
        String requestValue = getRequestValue(request);

        log.info("[IdempotentAspect] ({}) 요청 데이터 :: {}", requestKey, requestValue);

        // redis 만료시간
        int expireTime = idempotent.expireTime();

        // 중복 요청인지 체크
        Boolean isPoss = stringRedisTemplate
                .opsForValue()
                .setIfAbsent(requestKey, requestValue, expireTime, TimeUnit.SECONDS);

        // 중복 요청인 경우
        if(Boolean.FALSE.equals(isPoss)) {
            // 적절한 예외처리 handle
            handleRequestException(requestKey, requestValue);
        }
    }

    /**
     * 요청 key 조회
     */
    private String getRequestKey(final HttpServletRequest request) {
        String token = request.getHeader("requestKey");

        if(token == null)
            throw new IllegalArgumentException();

        return token;
    }

    /**
     * 요청 payload 조회
     */
    private String getRequestValue(final HttpServletRequest request) {
        // GET 요청이 아닌 경우 request body 데이터 조회
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;

            return new String(cachingRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        }
        // GET 요청인 경우 request parameter 데이터 조회
        else {
            return request.getQueryString();
        }
    }

    /**
     * 중복되는 요청인 경우 적절한 예외처리
     */
    private void handleRequestException(final String requestKey, final String requestValue) {
        // 기존의 요청 데이터 조회
        String originRequestValue = stringRedisTemplate.opsForValue().get(requestKey);
        log.info("[IdempotentAspect] ({}) 기존의 요청 데이터 :: {}", requestKey, originRequestValue);

        // 기존의 요청 데이터와 일치하지 않는 경우 잘못된 요청으로 판단
        if(!requestValue.isBlank() && !requestValue.equals(originRequestValue))
            throw new UnprocessableEntityException();
            // 기존의 요청 데이터와 일치하는 경우 중복 요청으로 판단
        else
            throw new ConflictException();
    }

}

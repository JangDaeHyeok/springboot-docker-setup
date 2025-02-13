package com.jdh.idempotent.config.exception.common;

import com.jdh.idempotent.config.exception.common.enums.ApiExceptionEnum;
import com.jdh.idempotent.config.idempotent.exception.ConflictException;
import com.jdh.idempotent.config.idempotent.exception.UnprocessableEntityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class ApiExceptionAdvice {

    // bad request exception (MethodArgumentNotValidException)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.info("[ApiExceptionAdvice] bad request exception :: ", e);

        return ResponseEntity
                .status(ApiExceptionEnum.BAD_REQUEST.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ApiExceptionEnum.BAD_REQUEST.getCode())
                        .errorMsg(ApiExceptionEnum.BAD_REQUEST.getMessage())
                        .build());
    }

    // bad request exception (IllegalArgumentException)
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, IllegalArgumentException e) {
        log.info("[ApiExceptionAdvice] bad request exception :: ", e);

        return ResponseEntity
                .status(ApiExceptionEnum.BAD_REQUEST.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ApiExceptionEnum.BAD_REQUEST.getCode())
                        .errorMsg(ApiExceptionEnum.BAD_REQUEST.getMessage())
                        .build());
    }

    // method not allowed exception
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, HttpRequestMethodNotSupportedException e) {
        log.info("[ApiExceptionAdvice] method not allowed exception :: ", e);

        return ResponseEntity
                .status(ApiExceptionEnum.METHOD_NOT_ALLOWED.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ApiExceptionEnum.METHOD_NOT_ALLOWED.getCode())
                        .errorMsg(ApiExceptionEnum.METHOD_NOT_ALLOWED.getMessage())
                        .build());
    }

    // not found exception
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, NoHandlerFoundException e) {
        log.info("[ApiExceptionAdvice] not found exception :: ", e);

        return ResponseEntity
                .status(ApiExceptionEnum.NOT_FOUND.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ApiExceptionEnum.NOT_FOUND.getCode())
                        .errorMsg(ApiExceptionEnum.NOT_FOUND.getMessage())
                        .build());
    }

    // conflict exception
    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, ConflictException e) {
        log.info("[ApiExceptionAdvice] conflict exception :: ", e);

        return ResponseEntity
                .status(ApiExceptionEnum.CONFLICT.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ApiExceptionEnum.CONFLICT.getCode())
                        .errorMsg(ApiExceptionEnum.CONFLICT.getMessage())
                        .build());
    }

    // unprocessable entity exception
    @ExceptionHandler({UnprocessableEntityException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, UnprocessableEntityException e) {
        log.info("[ApiExceptionAdvice] unprocessable entity exception :: ", e);

        return ResponseEntity
                .status(ApiExceptionEnum.UNPROCESSABLE_ENTITY.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ApiExceptionEnum.UNPROCESSABLE_ENTITY.getCode())
                        .errorMsg(ApiExceptionEnum.UNPROCESSABLE_ENTITY.getMessage())
                        .build());
    }

    // runtime(unchecked) exception
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, final RuntimeException e) {
        log.error("[ApiExceptionAdvice] runtime exception :: ", e);

        return ResponseEntity
                .status(ApiExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ApiExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .errorMsg(ApiExceptionEnum.RUNTIME_EXCEPTION.getMessage())
                        .build());
    }

    // exception
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(HttpServletRequest req, final Exception e) {
        log.error("[ApiExceptionAdvice] exception :: ", e);

        return ResponseEntity
                .status(ApiExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionEntity.builder()
                        .errorCode(ApiExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .errorMsg(ApiExceptionEnum.RUNTIME_EXCEPTION.getMessage())
                        .build());
    }
}
package com.jdh.idempotent.api.user.application.impl;

import com.jdh.idempotent.api.user.application.UserDelService;
import com.jdh.idempotent.api.user.domain.entity.User;
import com.jdh.idempotent.api.user.domain.repository.UserRepository;
import com.jdh.idempotent.api.user.exception.UserException;
import com.jdh.idempotent.api.user.exception.UserExceptionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDelServiceImpl implements UserDelService {

    private final UserRepository userRepository;

    /**
     * 사용자 삭제
     *
     * @param id user id
     */
    @Override
    @Transactional
    public void delUser(final long id) {
        // 사용자 정보 조회
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionResult.NOT_EXISTS));

        // 사용자 정보 삭제
        userRepository.delete(findUser);
    }

}

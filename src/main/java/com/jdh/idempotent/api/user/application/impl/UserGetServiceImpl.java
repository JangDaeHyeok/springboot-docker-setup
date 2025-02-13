package com.jdh.idempotent.api.user.application.impl;

import com.jdh.idempotent.api.user.application.UserGetService;
import com.jdh.idempotent.api.user.domain.repository.UserRepository;
import com.jdh.idempotent.api.user.dto.response.UserGetResponseDTO;
import com.jdh.idempotent.api.user.exception.UserException;
import com.jdh.idempotent.api.user.exception.UserExceptionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGetServiceImpl implements UserGetService {

    private final UserRepository userRepository;

    /**
     * 사용자 정보 조회
     *
     * @param id user id
     * @return 사용자 정보
     */
    @Override
    @Transactional(readOnly = true)
    public UserGetResponseDTO getUser(final long id) {
        return userRepository.findById(id)
                .map(UserGetResponseDTO::of)
                .orElseThrow(() -> new UserException(UserExceptionResult.NOT_EXISTS));
    }

}

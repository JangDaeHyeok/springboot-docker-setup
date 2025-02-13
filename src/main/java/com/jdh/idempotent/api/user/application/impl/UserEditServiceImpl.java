package com.jdh.idempotent.api.user.application.impl;

import com.jdh.idempotent.api.user.application.UserEditService;
import com.jdh.idempotent.api.user.domain.entity.User;
import com.jdh.idempotent.api.user.domain.repository.UserRepository;
import com.jdh.idempotent.api.user.dto.request.UserEditRequestDTO;
import com.jdh.idempotent.api.user.exception.UserException;
import com.jdh.idempotent.api.user.exception.UserExceptionResult;
import com.jdh.idempotent.config.idempotent.annotaion.Idempotent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserEditServiceImpl implements UserEditService {

    private final UserRepository userRepository;

    /**
     * 사용자 정보 수정
     *
     * @param userEditRequestDTO UserEditRequestDTO
     */
    @Override
    @Transactional
    public void editUser(final UserEditRequestDTO userEditRequestDTO) {
        // 사용자 정보 조회
        User findUser = userRepository.findById(userEditRequestDTO.getId())
                .orElseThrow(() -> new UserException(UserExceptionResult.NOT_EXISTS));

        // 수정될 사용자 값 입력
        findUser.editOf(userEditRequestDTO);

        userRepository.save(findUser);
    }

    /**
     * 사용자 나이 1살 증가
     *
     * @param id user id
     */
    @Override
    @Transactional
    @Idempotent
    public void editUserAgePlusOne(final long id) {
        // 사용자 정보 조회
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionResult.NOT_EXISTS));

        // 사용자 나이 1살 증가
        findUser.agePlusOne();

        userRepository.save(findUser);
    }

}

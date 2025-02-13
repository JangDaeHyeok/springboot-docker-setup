package com.jdh.idempotent.api.user.application.impl;

import com.jdh.idempotent.api.user.application.UserAddService;
import com.jdh.idempotent.api.user.domain.entity.User;
import com.jdh.idempotent.api.user.domain.repository.UserRepository;
import com.jdh.idempotent.api.user.dto.request.UserAddRequestDTO;
import com.jdh.idempotent.config.idempotent.annotaion.Idempotent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAddServiceImpl implements UserAddService {

    private final UserRepository userRepository;

    /**
     * 사용자 등록
     * @param userAddRequestDTO UserAddRequestDTO
     */
    @Override
    @Transactional
    @Idempotent(expireTime = 20)
    public void addUser(final UserAddRequestDTO userAddRequestDTO) {
        userRepository.save(User.addOf(userAddRequestDTO));
    }

}

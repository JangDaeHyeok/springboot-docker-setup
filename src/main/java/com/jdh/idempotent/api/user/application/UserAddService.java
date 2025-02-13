package com.jdh.idempotent.api.user.application;

import com.jdh.idempotent.api.user.dto.request.UserAddRequestDTO;

public interface UserAddService {

    /**
     * 사용자 등록
     * @param userAddRequestDTO UserAddRequestDTO
     */
    void addUser(final UserAddRequestDTO userAddRequestDTO);

}

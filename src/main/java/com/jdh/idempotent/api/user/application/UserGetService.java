package com.jdh.idempotent.api.user.application;

import com.jdh.idempotent.api.user.dto.response.UserGetResponseDTO;

public interface UserGetService {

    /**
     * 사용자 정보 조회
     *
     * @param id user id
     * @return 사용자 정보
     */
    UserGetResponseDTO getUser(final long id);

}

package com.jdh.idempotent.api.user.application;

import com.jdh.idempotent.api.user.dto.request.UserEditRequestDTO;

public interface UserEditService {

    /**
     * 사용자 정보 수정
     *
     * @param userEditRequestDTO UserEditRequestDTO
     */
    void editUser(final UserEditRequestDTO userEditRequestDTO);

    /**
     * 사용자 나이 1살 증가
     *
     * @param id user id
     */
    void editUserAgePlusOne(final long id);

}

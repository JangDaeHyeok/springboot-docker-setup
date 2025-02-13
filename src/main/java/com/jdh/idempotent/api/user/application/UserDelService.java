package com.jdh.idempotent.api.user.application;

public interface UserDelService {

    /**
     * 사용자 삭제
     *
     * @param id user id
     */
    void delUser(final long id);

}

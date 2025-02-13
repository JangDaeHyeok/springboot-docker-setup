package com.jdh.idempotent.api.user.dto.response;

import com.jdh.idempotent.api.user.domain.entity.User;
import lombok.Builder;

@Builder
public record UserGetResponseDTO(long id, String name, String tel, int age) {

    public static UserGetResponseDTO of(User user) {
        return UserGetResponseDTO.builder()
                .id(user.getId())
                .name(user.getUserInfo().getName())
                .tel(user.getUserInfo().getTel())
                .age(user.getUserInfo().getAge())
                .build();
    }

}

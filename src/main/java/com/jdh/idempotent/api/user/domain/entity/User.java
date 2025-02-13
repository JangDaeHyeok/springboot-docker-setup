package com.jdh.idempotent.api.user.domain.entity;

import com.jdh.idempotent.api.common.entity.RegModDt;
import com.jdh.idempotent.api.user.domain.entity.value.UserInfo;
import com.jdh.idempotent.api.user.dto.request.UserAddRequestDTO;
import com.jdh.idempotent.api.user.dto.request.UserEditRequestDTO;
import com.jdh.idempotent.api.user.exception.UserException;
import com.jdh.idempotent.api.user.exception.UserExceptionResult;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user SET del_yn = true WHERE id = ?")
@SQLRestriction("del_yn = false")
public class User extends RegModDt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private UserInfo userInfo;

    private boolean delYn = Boolean.FALSE; // 삭제 여부 기본값 false

    public static User addOf(UserAddRequestDTO userAddRequestDTO) {
        UserInfo userInfo = UserInfo.builder()
                .name(userAddRequestDTO.getName())
                .tel(userAddRequestDTO.getTel())
                .age(userAddRequestDTO.getAge())
                .build();

        return User.builder()
                .userInfo(userInfo)
                .build();
    }

    public void editOf(UserEditRequestDTO userEditRequestDTO) {
        this.userInfo = UserInfo.builder()
                .name(userEditRequestDTO.getName())
                .tel(userEditRequestDTO.getTel())
                .age(userEditRequestDTO.getAge())
                .build();
    }

    public void agePlusOne() {
        if(this.userInfo == null)
            throw new UserException(UserExceptionResult.NOT_EXISTS);

        this.userInfo.agePlusOne();
    }

}

package com.jdh.idempotent.api.user.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserEditRequestDTO {

    @NotNull
    @Min(1)
    private long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String tel;

    @NotNull
    @Min(1)
    private int age;

}

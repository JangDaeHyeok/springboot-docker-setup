package com.jdh.idempotent.api.user.controller;

import com.jdh.idempotent.api.common.response.entity.ApiResponseEntity;
import com.jdh.idempotent.api.user.application.UserAddService;
import com.jdh.idempotent.api.user.application.UserDelService;
import com.jdh.idempotent.api.user.application.UserEditService;
import com.jdh.idempotent.api.user.application.UserGetService;
import com.jdh.idempotent.api.user.dto.request.UserAddRequestDTO;
import com.jdh.idempotent.api.user.dto.request.UserEditRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "user")
public class UserController {

    private final UserGetService userGetService;

    private final UserAddService userAddService;

    private final UserEditService userEditService;

    private final UserDelService userDelService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseEntity> get(@PathVariable("id") Long id) {
        // 사용자 정보 조회
        var result = userGetService.getUser(id);

        return ApiResponseEntity.successResponseEntity(result);
    }

    @PostMapping
    public ResponseEntity<ApiResponseEntity> add(@RequestBody @Valid UserAddRequestDTO userAddRequestDTO) {
        // 사용자 정보 저장
        userAddService.addUser(userAddRequestDTO);

        return ApiResponseEntity.successResponseEntity();
    }

    @PutMapping
    public ResponseEntity<ApiResponseEntity> edit(@RequestBody @Valid UserEditRequestDTO userEditRequestDTO) {
        // 사용자 정보 수정
        userEditService.editUser(userEditRequestDTO);

        return ApiResponseEntity.successResponseEntity();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseEntity> editAgePlusOne(@PathVariable("id") Long id) {
        // 사용자 나이 1살 증가
        userEditService.editUserAgePlusOne(id);

        return ApiResponseEntity.successResponseEntity();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseEntity> delete(@PathVariable("id") Long id) {
        // 사용자 정보 삭제
        userDelService.delUser(id);

        return ApiResponseEntity.successResponseEntity();
    }

}

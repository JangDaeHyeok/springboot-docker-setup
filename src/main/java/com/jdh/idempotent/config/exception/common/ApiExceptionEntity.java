package com.jdh.idempotent.config.exception.common;

import lombok.Builder;

@Builder
public record ApiExceptionEntity(String errorCode, String errorMsg) {
}
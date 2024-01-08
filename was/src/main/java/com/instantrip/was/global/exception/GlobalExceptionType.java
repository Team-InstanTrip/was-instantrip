package com.instantrip.was.global.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GlobalExceptionType implements BaseExceptionType {
    MISSING_INPUT("400", HttpStatus.BAD_REQUEST, "필수 입력값이 누락되었습니다."),
    INTERNAL_ERROR("99", HttpStatus.BAD_GATEWAY, "Internal Error")
    ;

    private String errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}

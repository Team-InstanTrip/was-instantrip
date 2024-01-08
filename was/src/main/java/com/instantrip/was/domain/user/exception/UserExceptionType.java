package com.instantrip.was.domain.user.exception;

import com.instantrip.was.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserExceptionType implements BaseExceptionType {
    USER_NOT_FOUND("404", HttpStatus.NOT_FOUND, "회원정보를 찾을 수 없습니다"),
    USER_UNAUTHORIZED("401", HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    USER_FORBIDDEN("403", HttpStatus.FORBIDDEN, "권한이 없습니다.")
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

package com.instantrip.was.domain.message.exception;

import com.instantrip.was.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MessageExceptionType implements BaseExceptionType {
    MESSAGE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "메시지가 존재하지 않습니다."),
    MESSAGE_EXPIRED(403, HttpStatus.FORBIDDEN, "만료된 메시지 입니다.")
    ;

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    @Override
    public int getErrorCode() {
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

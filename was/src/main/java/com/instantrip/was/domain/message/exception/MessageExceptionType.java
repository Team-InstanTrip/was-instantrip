package com.instantrip.was.domain.message.exception;

import com.instantrip.was.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MessageExceptionType implements BaseExceptionType {
    MESSAGE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "메시지가 존재하지 않습니다."),
    MESSAGE_EXPIRED(403, HttpStatus.FORBIDDEN, "만료된 메시지입니다."),
    MESSAGE_ALREADY_LIKED(40, HttpStatus.BAD_REQUEST, "이미 좋아요 표시한 메시지입니다."),
    MESSAGE_ALREADY_DISLIKED(41, HttpStatus.BAD_REQUEST, "이미 싫어요 표시한 메시지입니다.")
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

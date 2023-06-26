package com.instantrip.was.domain.message.exception;

import com.instantrip.was.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class MessageNotFoundException extends BaseException {
    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}

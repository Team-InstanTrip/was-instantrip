package com.instantrip.was.domain.message.exception;

import com.instantrip.was.global.exception.BaseException;
import com.instantrip.was.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageException extends BaseException {
    private BaseExceptionType exceptionType;

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}

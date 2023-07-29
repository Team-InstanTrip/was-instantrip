package com.instantrip.was.domain.user.exception;

import com.instantrip.was.global.exception.BaseException;
import com.instantrip.was.global.exception.BaseExceptionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserException extends BaseException {
    private BaseExceptionType exceptionType;

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}

package com.instantrip.was.global.exception;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    String getErrorCode();
    HttpStatus getHttpStatus();
    String getErrorMessage();
}

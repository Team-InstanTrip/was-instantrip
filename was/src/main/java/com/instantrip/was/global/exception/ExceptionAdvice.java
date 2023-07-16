package com.instantrip.was.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleBaseException(BaseException be){
        int errorCode = be.getExceptionType().getErrorCode();
        String message = be.getExceptionType().getErrorMessage();

        log.error("BaseException errorCode : {}", errorCode);
        log.error("BaseException errorMessage : {}", message);

        ExceptionDto dto = new ExceptionDto(errorCode, message);
        return new ResponseEntity(dto, be.getExceptionType().getHttpStatus());
    }

    @Data
    @AllArgsConstructor
    static class ExceptionDto {
        private Integer errorCode;
        private String message;
    }
}

package com.instantrip.was.global.exception;

import com.instantrip.was.global.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<Void>> handleBaseException(BaseException be){
        String errorCode = be.getExceptionType().getErrorCode();
        String message = be.getExceptionType().getErrorMessage();

        log.info("BaseException errorCode : {}", errorCode);
        log.info("BaseException errorMessage : {}", message);

        BaseResponse<Void> baseResponse = new BaseResponse<>(errorCode, message);
        return new ResponseEntity<BaseResponse<Void>>(baseResponse, be.getExceptionType().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleException(Exception e) {
        log.error("", e);
        BaseResponse<String> baseResponse = new BaseResponse<>(GlobalExceptionType.INTERNAL_ERROR.getErrorCode(),
                GlobalExceptionType.INTERNAL_ERROR.getErrorMessage(), e.getMessage());
        return new ResponseEntity<BaseResponse<String>>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

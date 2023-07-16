package com.instantrip.was.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {
    String responseCode;
    HttpStatus httpStatus;
    String message;
    T data;
}

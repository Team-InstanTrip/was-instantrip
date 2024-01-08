package com.instantrip.was.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseResponse<T> {
    private final String code;
    private final String message;
    private T data;

    public static <T> BaseResponse<T> ok(String message, T data) {
        return new BaseResponse<T>("00", message, data);
    }

    public static <T> BaseResponse<T> ok(String message) {
        return new BaseResponse<T>("00", message);
    }
}

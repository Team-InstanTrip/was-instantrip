package com.instantrip.was.global.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class BaseResponse<T> {
    private final int code;
    private final String message;
    private T data;
}

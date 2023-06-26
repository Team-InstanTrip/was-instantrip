package com.instantrip.was.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class ErrorResponse {
    private BaseException baseException;
}

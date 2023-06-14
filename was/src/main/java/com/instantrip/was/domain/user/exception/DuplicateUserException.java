package com.instantrip.was.domain.user.exception;

import com.instantrip.was.global.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class DuplicateUserException extends BaseException {
    private String duplicatedField;

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_ACCEPTABLE;
    }

    @Override
    public String getMessage() {
        return duplicatedField + "이/가 중복됩니다.";
    }
}

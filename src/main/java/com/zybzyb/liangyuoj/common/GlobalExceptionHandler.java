package com.zybzyb.liangyuoj.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.zybzyb.liangyuoj.common.exception.CommonException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = CommonException.class)
    public <T> Result<T> commonExceptionHandler(CommonException e) {
        log.warn("CommonException:", e);
        return Result.fail(e.getCommonErrorCode());
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public <T> Result<T> exceptionHandler(Exception e) {
        log.error("Exception:", e);
        return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}

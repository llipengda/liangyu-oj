package com.zybzyb.liangyuoj.common.exception;

import com.zybzyb.liangyuoj.common.CommonErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用异常
 */
@Getter
@AllArgsConstructor
public class CommonException extends RuntimeException {
    /**
     * 错误码
     */
    private final CommonErrorCode commonErrorCode;

    /**
     * 错误信息
     */
    private final String errorMessage;

    public CommonException(CommonErrorCode commonErrorCode) {
        this.commonErrorCode = commonErrorCode;
        this.errorMessage = commonErrorCode.getErrorMessage();
    }

    public CommonException() {
        this.commonErrorCode = CommonErrorCode.UNKNOWN_ERROR;
        this.errorMessage = CommonErrorCode.UNKNOWN_ERROR.getErrorMessage();
    }
}

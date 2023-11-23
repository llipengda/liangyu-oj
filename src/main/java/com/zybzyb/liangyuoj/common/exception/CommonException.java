package com.zybzyb.liangyuoj.common.exception;

import com.zybzyb.liangyuoj.common.CommonErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用异常
 */
@Getter
@AllArgsConstructor
public class CommonException extends Exception {
    /**
     * 错误码
     */
    private final CommonErrorCode commonErrorCode;

    /**
     * 错误信息
     */
    private final String errorMessage;

    public CommonException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode.getCode() + ": " + commonErrorCode.getMsg());
        this.commonErrorCode = commonErrorCode;
        this.errorMessage = commonErrorCode.getMsg();
    }

    public CommonException() {
        super(CommonErrorCode.UNKNOWN_ERROR.getCode() + ": " + CommonErrorCode.UNKNOWN_ERROR.getMsg());
        CommonErrorCode unknownError = CommonErrorCode.UNKNOWN_ERROR;
        this.commonErrorCode = unknownError;
        this.errorMessage = unknownError.getMsg();
    }
}

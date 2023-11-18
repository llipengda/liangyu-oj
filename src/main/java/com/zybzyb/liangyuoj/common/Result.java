package com.zybzyb.liangyuoj.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用返回结果
 */
@Data
@AllArgsConstructor
public class Result<T> {
    /**
     * 代码
     */
    private Integer code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 信息
     */
    private String msg;

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(CommonErrorCode.SUCCESS.getErrorCode(), data, "操作成功");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @param <T>       数据类型
     * @return Result
     */
    public static <T> Result<T> fail(CommonErrorCode errorCode) {
        return new Result<>(errorCode.getErrorCode(), null, errorCode.getErrorMessage());
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @param msg       信息
     * @param data      数据
     * @param <T>       数据类型
     * @return Result
     */
    public static <T> Result<T> fail(CommonErrorCode errorCode, T data, String msg) {
        return new Result<>(errorCode.getErrorCode(), data, msg);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @param data      数据
     * @param <T>       数据类型
     * @return Result
     */
    public static <T> Result<T> fail(CommonErrorCode errorCode, T data) {
        return new Result<>(errorCode.getErrorCode(), data, errorCode.getErrorMessage());
    }

    /**
     * 失败
     *
     * @param msg 信息
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(CommonErrorCode.UNKNOWN_ERROR.getErrorCode(), null, msg);
    }
}

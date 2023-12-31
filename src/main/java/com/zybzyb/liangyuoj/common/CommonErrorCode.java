package com.zybzyb.liangyuoj.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用错误码
 */
@Getter
@AllArgsConstructor
public enum CommonErrorCode {
    SUCCESS(200, "成功"),
    UNKNOWN_ERROR(400, "未知错误"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    PARAMETER_ERROR(4001, "参数错误"),
    USER_NOT_FOUND(4002, "用户不存在"),
    TOKEN_ERROR(4003, "token 校验失败,"),
    DATA_ERROR(4004, "邮箱或密码错误"),
    SQL_ERROR(4005, "SQL 错误"),
    PASSWORD_SAME(4006, "新旧密码相同"),
    EMAIL_HAS_BEEN_SIGNED_UP(4007, "邮箱已被注册"),
    SEND_EMAIL_FAILED(4008, "发送邮件失败"),
    VERIFICATION_CODE_HAS_EXPIRED(4009, "验证码已过期"),
    FILE_NAME_EMPTY(4010, "文件名为空"),
    NOT_A_PICTURE(4011, "该文件不是图片"),
    PROBLEM_NOT_FOUND(4012, "问题不存在");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String msg;
}

package com.zybzyb.liangyuoj.common;

import java.util.HashMap;
import java.util.Map;

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
    EMAIL_ALREADY_EXIST(4002, "邮箱已存在"),
    TOKEN_ERROR(4003, "token 校验失败,"),
    ;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误信息
     */
    private final String msg;

    /**
     * 转换为 Map
     * @return Map
     */
    public Map<String, Object> toMap() {
        Map<String, Object> res = new HashMap<>();
        res.put("code", this.code);
        res.put("msg", this.msg);
        return res;
    }
}

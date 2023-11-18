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
    ;

    /**
     * 错误码
     */
    private final Integer errorCode;

    /**
     * 错误信息
     */
    private final String errorMessage;

    /**
     * 转换为 Map
     * @return Map
     */
    public Map<String, Object> toMap() {
        Map<String, Object> res = new HashMap<>();
        res.put("errorCode", this.errorCode);
        res.put("errorMessage", this.errorMessage);
        return res;
    }
}

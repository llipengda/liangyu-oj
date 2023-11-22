package com.zybzyb.liangyuoj.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "登录响应")
public class LoginResponse {
    /**
     * token
     */
    private String token;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 用户类型
     */
    private Integer type;
}

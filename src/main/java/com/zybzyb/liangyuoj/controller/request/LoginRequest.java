package com.zybzyb.liangyuoj.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "登录请求")
public class LoginRequest {

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

}

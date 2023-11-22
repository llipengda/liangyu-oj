package com.zybzyb.liangyuoj.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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


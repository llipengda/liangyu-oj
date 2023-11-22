package com.zybzyb.liangyuoj.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpRequest {

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;
}

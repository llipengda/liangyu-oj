package com.zybzyb.liangyuoj.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

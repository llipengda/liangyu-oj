package com.zybzyb.liangyuoj.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "JWT 用户")
public class JWTUser {
    private Long id;
    private Integer type;

    public JWTUser(User user) {
        this.id = user.getId();
        this.type = user.getType();
    }
}

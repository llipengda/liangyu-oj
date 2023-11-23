package com.zybzyb.liangyuoj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTUser {
    private Long id;
    private Integer type;

    public JWTUser(User user) {
        this.id = user.getId();
        this.type = user.getType();
    }
}

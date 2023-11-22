package com.zybzyb.liangyuoj.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "更新用户请求")
public class UpdateUserRequest {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实名字
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String motto;

    /**
     * 年级
     */
    private String grade;
}

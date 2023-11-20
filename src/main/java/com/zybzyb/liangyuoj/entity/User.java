package com.zybzyb.liangyuoj.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "用户")
public class User implements Serializable {

    /**
     * 用户 ID
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

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

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

}

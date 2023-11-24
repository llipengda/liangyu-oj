package com.zybzyb.liangyuoj.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "用户")
public class User implements Serializable {

    /**
     * 用户 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * 用户类型
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 提交数
     */
    private Integer submitted;

    /**
     * 做题数
     */
    private Integer solved;

}

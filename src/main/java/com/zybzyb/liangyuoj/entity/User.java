package com.zybzyb.liangyuoj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private Long id;

    private String createAt;

    private String nickname;

    private String name;

    private Integer gender;

    private String school;

    private String department;

    private String major;

    private String grade;

    private String telephone;

    private String QQ;

    private String wechatNum;

    private String portrait;

}
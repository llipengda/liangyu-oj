package com.zybzyb.liangyuoj.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "题目简要信息")
@AllArgsConstructor
public class BriefProblem {

    /**
     * 题目 ID
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 通过数
     */
    private Integer accepted;

    /**
     * 提交数
     */
    private Integer submit;

    /**
     * 难度
     */
    private Integer difficulty;
}
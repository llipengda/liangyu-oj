package com.zybzyb.liangyuoj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "提交")
public class Submission {

    /**
     * 提交 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 题目 ID
     */
    private Long problemId;

    /**
     * 题目名字
     */
    private String problemName;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 代码
     */
    private String code;

    /**
     * 结果
     */
    private String result;

    /**
     * 用时
     */
    private Double time;

    /**
     * 内存
     */
    private Long memory;
}
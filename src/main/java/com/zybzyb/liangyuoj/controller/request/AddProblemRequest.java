package com.zybzyb.liangyuoj.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "添加题目请求")
public class AddProblemRequest {

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 第几章的题目
     */
    private Integer chapter;

    /**
     * 提前写好的code
     */
    private String reservedCode;

    /**
     * 输入描述
     */
    private String input;

    /**
     * 输出描述
     */
    private String output;

    /**
     * 所有输入
     */
    private List<String> allInput;

    /**
     * 所有输出
     */
    private List<String> allOutput;

    /**
     * 提示
     */
    private String hint;

    /**
     * 时间限制
     */
    private Integer timeLimit;

    /**
     * 内存限制
     */
    private Integer memoryLimit;

    /**
     * 难度
     */
    private Integer difficulty;
}
package com.zybzyb.liangyuoj.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "题目简要信息")
@AllArgsConstructor
public class ProblemDto {

    /**
     * 题目 ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 第几章的题目
     */
    private Integer chapter;

    /**
     * 章节名
     */
    private String chapterName;

    /**
     * 题目描述
     */
    private String description;

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
     * 样例输入
     */
    private String sampleInput;

    /**
     * 样例输出
     */
    private String sampleOutput;

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
     * 通过数
     */
    private Integer accepted;

    /**
     * 提交数
     */
    private Integer submitted;

    /**
     * 难度
     */
    private Integer difficulty;

    public ProblemDto(Problem p){
        this.id = p.getId();
        this.createTime = p.getCreateTime();
        this.deleteTime = p.getDeleteTime();
        this.title = p.getTitle();
        this.chapter = p.getChapter();
        this.chapterName = p.getChapterName();
        this.description = p.getDescription();
        this.reservedCode = p.getReservedCode();
        this.input = p.getInput();
        this.output = p.getOutput();
        this.sampleInput = p.getSampleInput();
        this.sampleOutput = p.getSampleOutput();
        this.hint = p.getHint();
        this.timeLimit = p.getTimeLimit();
        this.memoryLimit = p.getMemoryLimit();
        this.accepted = p.getAccepted();
        this.submitted = p.getSubmitted();
        this.difficulty = p.getDifficulty();
    }
}

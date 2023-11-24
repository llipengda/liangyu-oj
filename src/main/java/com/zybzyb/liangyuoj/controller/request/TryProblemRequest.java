package com.zybzyb.liangyuoj.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "做题请求")
public class TryProblemRequest {
    /**
     * 题目 ID
     */
    private Long problemId;

    /**
     * 代码
     */
    private String code;

}
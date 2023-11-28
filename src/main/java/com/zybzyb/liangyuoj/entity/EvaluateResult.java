package com.zybzyb.liangyuoj.entity;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "评测结果")
@AllArgsConstructor
public class EvaluateResult {
    /**
     * 评测状态
     */
    private EvaluateStatus status;

    /**
     * 评测信息
     */
    private String message;

    /**
     * 所用时间
     */
    private Double time;

    /**
     * 所用内存
     */
    private Long memory;

    public EvaluateResult() {
        this.status = EvaluateStatus.AC;
        this.message = "评测通过";
        this.time = 0.0;
        this.memory = 0L;
    }
}

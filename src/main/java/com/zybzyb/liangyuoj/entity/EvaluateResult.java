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

    public EvaluateResult() {
        this.status = EvaluateStatus.AC;
        this.message = "测试通过";
    }
}

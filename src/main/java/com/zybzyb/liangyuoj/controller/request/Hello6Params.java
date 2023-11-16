package com.zybzyb.liangyuoj.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Hello6 参数测试
 */
@Schema
@Data
public class Hello6Params {
    /**
     * 名字
     */
    @NotNull
    private String name;

    /**
     * 年龄
     */
    private Integer age;
}

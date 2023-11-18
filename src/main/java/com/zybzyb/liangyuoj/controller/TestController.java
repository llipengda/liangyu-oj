package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.controller.request.Hello6Params;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Validated
@Tag(name = "测试", description = "springdoc 测试")
public class TestController {
    // 自动 NotNull
    @Operation(summary = "自动 NotNull", description = "自动 NotNull")
    @GetMapping("/hello1")
    public Result<String> hello1(@NotNull String name) {
        return Result.success("hello " + name);
    }

    // 自动 Nullable
    @Operation(summary = "自动 Nullable", description = "自动 Nullable")
    @GetMapping("/hello2")
    public Result<String> hello2(@Nullable String name) {
        return Result.success("hello " + name);
    }

    // 手动设置参数
    @Operation(summary = "手动设置参数", description = "手动设置参数")
    @Parameter(name = "name", description = "名字", required = true)
    @GetMapping("/hello3")
    public Result<String> hello3(@NotNull String name) {
        return Result.success("hello " + name);
    }

    /**
     * 或者可以直接使用 JavaDoc 注释
     *
     * @param name 名字
     * @return hello + 名字
     */
    @GetMapping("/hello4")
    public Result<String> hello4(@NotNull String name) {
        return Result.success("hello " + name);
    }

    /**
     * 路径参数
     *
     * @param name 名字
     * @return hello + 名字
     */
    @GetMapping("/hello5/{name}")
    public Result<String> hello5(@PathVariable String name) {
        return Result.success("hello " + name);
    }

    /**
     * 请求体参数
     *
     * @param params 参数
     * @return hello + 名字
     */
    @PostMapping("/hello6")
    public Result<String> hello6(@NotNull @RequestBody Hello6Params params) {
        return Result.success("hello " + params.getName());
    }
}

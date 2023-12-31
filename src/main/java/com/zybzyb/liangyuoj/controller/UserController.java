package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.controller.request.UpdateUserRequest;
import com.zybzyb.liangyuoj.entity.Submission;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.service.UserService;
import com.zybzyb.liangyuoj.util.JWTUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "用户接口", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     * 
     * @param request 请求
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<User> info(HttpServletRequest request) {
        Long userId = JWTUtil.getUserIdFromRequest(request);
        return Result.success(userService.getUserInfo(userId));
    }

    /**
     * 用户更新信息
     * 
     * @param updateUserRequest 更新信息
     * @param request           请求
     * @return 更新结果
     * @throws Exception 异常
     */
    @PutMapping(value = "/update", produces = "application/json")
    public Result<User> update(@RequestBody UpdateUserRequest updateUserRequest, HttpServletRequest request) throws Exception {
        Long userId = JWTUtil.getUserIdFromRequest(request);
        return Result.success(userService.updateUser(updateUserRequest, userId));
    }

    /**
     * 上传图片
     * @param file 文件
     * @return 图片地址
     * @throws Exception 异常
     */
    @PostMapping("/image")
    public Result<String> uploadImage(@RequestBody MultipartFile file) throws Exception {
        return Result.success(userService.uploadImage(file));
    }

    /**
     * 获取最近提交
     * @return 最近提交
     */
    @GetMapping("/getRecentSubmission")
    public Result<List<Submission>> getRecentSubmission(HttpServletRequest request) {
        Long userId = JWTUtil.getUserIdFromRequest(request);
        return Result.success(userService.getRecentSubmission(userId));
    }

}





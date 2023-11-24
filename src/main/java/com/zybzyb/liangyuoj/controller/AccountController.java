package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.annotation.NoAuth;
import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.controller.request.LoginRequest;
import com.zybzyb.liangyuoj.controller.request.SignUpRequest;
import com.zybzyb.liangyuoj.controller.response.LoginResponse;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.service.AccountService;
import com.zybzyb.liangyuoj.util.JWTUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Validated
@Tag(name = "账号接口", description = "账号相关接口")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 用户注册
     * 
     * @param signUpRequest 注册信息
     * @return 注册结果
     * @throws CommonException
     */
    @NoAuth
    @PostMapping(value = "/signUp", produces = "application/json")
    public Result<Void> signUp(@RequestBody SignUpRequest signUpRequest) throws CommonException {
        accountService.signUp(signUpRequest);
        return Result.success(null);
    }

    /**
     * 用户登录
     * 
     * @param loginRequest 登录信息
     * @return 登录结果
     * @throws CommonException
     */
    @NoAuth
    @PostMapping(value = "/login", produces = "application/json")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws CommonException {
        return Result.success(accountService.login(loginRequest));
    }

    /**
     * 用户更新密码
     * 
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param request     请求
     * @return 更新结果
     * @throws CommonException
     */
    @PutMapping(value = "/updatePassword", produces = "application/json")
    public Result<User> updatePassword(String oldPassword, String newPassword, HttpServletRequest request)
        throws CommonException {
        Long userId = JWTUtil.getUserIdFromRequest(request);
        return Result.success(accountService.updatePassword(oldPassword, newPassword, userId));
    }

    /**
     * 用户注销
     * 
     * @return 更新结果
     */
    @DeleteMapping(value = "/delete", produces = "application/json")
    public Result<Boolean> delete(HttpServletRequest request) {
        Long userId = JWTUtil.getUserIdFromRequest(request);
        return Result.success(accountService.delete(userId));
    }

}

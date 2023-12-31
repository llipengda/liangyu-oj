package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.annotation.NoAuth;
import com.zybzyb.liangyuoj.common.CommonErrorCode;
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
     * @throws CommonException 通用异常
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
     * @throws CommonException 通用异常
     */
    @NoAuth
    @PostMapping(value = "/login", produces = "application/json")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws CommonException {
        return Result.success(accountService.login(loginRequest));
    }

    /**
     * 用户更新密码
     * 
     * @param email       邮箱
     * @param newPassword 新密码
     * @return 更新结果
     * @throws CommonException 通用异常
     */
    @NoAuth
    @PutMapping(value = "/updatePassword", produces = "application/json")
    public Result<User> updatePassword(String email, String newPassword)
        throws CommonException {
        return Result.success(accountService.updatePassword(email, newPassword));
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

    /**
     * 发送验证码
     * 
     * @param email 邮箱
     * @param type  类型 0:注册 1:修改密码
     * @return 发送结果
     * @throws CommonException 通用异常
     */
    @NoAuth
    @PostMapping(value = "/sendCode", produces = "application/json")
    public Result<Boolean> sendCode(String email, Integer type) throws CommonException {
        if (type == 0) {
            return Result.success(accountService.sendVerifyCode(email));
        } else if (type == 1) {
            return Result.success(accountService.sendConfirmEmail(email));
        } else {
            throw new CommonException(CommonErrorCode.PARAMETER_ERROR);
        }
    }

    /**
     * 验证验证码
     * 
     * @param email 邮箱
     * @param code  验证码
     * @return 验证结果
     * @throws CommonException 通用异常
     */
    @NoAuth
    @PostMapping(value = "/verifyCode", produces = "application/json")
    public Result<Boolean> verifyCode(String email, String code) throws CommonException {
        return Result.success(accountService.verifyCode(email, code));
    }

    /**
     * 检查用户名是否可用
     * 
     * @param name 用户名
     * @return 检查结果
     * @throws CommonException 通用异常
     */
    @NoAuth
    @PostMapping(value = "/checkName", produces = "application/json")
    public Result<Boolean> checkName(String name) throws CommonException {
        return Result.success(accountService.checkName(name));
    }

    /**
     * 检查邮箱是否可用
     * 
     * @param email 邮箱
     * @return 检查结果
     * @throws CommonException 通用异常
     */
    @NoAuth
    @PostMapping(value = "/checkEmail", produces = "application/json")
    public Result<Boolean> checkEmail(String email) throws CommonException {
        return Result.success(accountService.checkEmail(email));
    }

}

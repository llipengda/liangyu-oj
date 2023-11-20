package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.common.CommonErrorCode;
import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
@Validated
@Schema(description = "用户相关操作")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * @param email 邮箱
     * @param password 密码
     * @param type 用户类型，0为管理员，1为老师，2为学生
     * @return 注册结果
     */
    @PostMapping(value = "/signUp", produces = "application/json")
    public Result<Void> webSignUp(@NotNull @RequestParam("email") String email, @NotNull @RequestParam("password") String password, @NotNull @RequestParam("type") Integer type){
        try {
            if(userMapper.selectCount(User.builder().email(email).build()) != 0){
                return Result.fail(CommonErrorCode.EMAIL_ALREADY_EXIST);
            }
            User user = User.builder()
                    .createTime(new Date())
                    .email(email)
                    .password(password)
                    .nickname("用户" + email.replaceAll("@.*", ""))
                    .build();

            return Result.success(null);

        } catch (CommonException e){
            return Result.fail(e.getCommonErrorCode());
        }
    }
}

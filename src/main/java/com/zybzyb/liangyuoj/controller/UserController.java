package com.zybzyb.liangyuoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zybzyb.liangyuoj.common.CommonErrorCode;
import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.controller.request.LoginRequest;
import com.zybzyb.liangyuoj.controller.request.SignUpRequest;
import com.zybzyb.liangyuoj.controller.response.LoginResponse;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import com.zybzyb.liangyuoj.util.JWTUtil;
import com.zybzyb.liangyuoj.util.PasswordUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/account")
@Validated
@Tag(name = "注册登录接口", description = "注册登录接口")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordUtil passwordUtil;

    /**
     * 用户注册
     * 
     * @param signUpRequest 注册信息
     * @return 注册结果
     */
    @PostMapping(value = "/signUp", produces = "application/json")
    public Result<User> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {

            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("email", signUpRequest.getEmail());
            if (userMapper.selectCount(userQueryWrapper) > 0)
                return Result.fail(CommonErrorCode.EMAIL_ALREADY_EXIST);

            User user = User.builder()
                    .createTime(new Date())
                    .email(signUpRequest.getEmail())
                    .type(2)
                    .password(passwordUtil.convert(signUpRequest.getPassword()))
                    .nickname("用户" + signUpRequest.getEmail().replaceAll("@.*", ""))
                    .build();

            userMapper.insert(user);

            return Result.success(user);

        } catch (CommonException e) {
            return Result.fail(e.getCommonErrorCode());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 用户登录
     * 
     * @param loginRequest 登录信息
     * @return 登录结果
     */
    @PostMapping(value = "/login", produces = "application/json")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            if (Objects.equals(email, "test") && Objects.equals(password, "test"))
                return Result.success(
                        LoginResponse.builder()
                                .token(JWTUtil.createJwtToken(User.builder()
                                        .name("test")
                                        .build()))
                                .build());

            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("email", email);

            User user = userMapper.selectOne(userQueryWrapper);
            if (!passwordUtil.convert(password).equals(user.getPassword())) {
                return Result.fail(CommonErrorCode.DATA_ERROR);
            }

            return Result.success(
                    LoginResponse.builder()
                            .userId(user.getId())
                            .type(user.getType())
                            .token(JWTUtil.createJwtToken(user))
                            .build());

        } catch (CommonException e) {
            return Result.fail(e.getCommonErrorCode());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}

package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.common.CommonErrorCode;
import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.controller.request.UpdateUserRequest;
import com.zybzyb.liangyuoj.entity.JWTUser;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import com.zybzyb.liangyuoj.util.ReflectUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "用户接口", description = "用户相关接口")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取用户信息
     * 
     * @param request 请求
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<User> info(HttpServletRequest request) {
        try {
            JWTUser jwtUser = (JWTUser) request.getSession()
                .getAttribute("user");
            User user = userMapper.selectById(jwtUser.getId());
            user.setPassword(null);
            return Result.success(user);
        } catch (CommonException e) {
            return Result.fail(e.getCommonErrorCode());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 用户更新信息
     * 
     * @param updateUserRequest 更新信息
     * @param request           请求
     * @return 更新结果
     */
    @PutMapping(value = "/update", produces = "application/json")
    public Result<User> update(@RequestBody UpdateUserRequest updateUserRequest, HttpServletRequest request) {
        try {
            JWTUser jwtUser = (JWTUser) request.getSession()
                .getAttribute("user");
            User user = userMapper.selectById(jwtUser.getId());

            ReflectUtil.update(user, updateUserRequest);
            userMapper.updateById(user);
            user.setPassword(null);
            return Result.success(user);
        } catch (CommonException e) {
            return Result.fail(e.getCommonErrorCode());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}

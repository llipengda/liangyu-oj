package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.controller.request.UpdateUserRequest;
import com.zybzyb.liangyuoj.entity.JWTUser;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import com.zybzyb.liangyuoj.util.StringUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "用户接口", description = "用户相关接口")
public class UserInfoController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户更新信息
     * 
     * @param updateUserRequest 更新信息
     * @param request           请求
     * @return 更新结果
     */
    @PostMapping(value = "/update", produces = "application/json")
    public Result<User> update(@RequestBody UpdateUserRequest updateUserRequest, HttpServletRequest request) {
        JWTUser jwtUser = (JWTUser) request.getSession().getAttribute("user");

        User user = userMapper.selectById(jwtUser.getId());

        if (!Objects.equals(updateUserRequest.getAvatar(), user.getAvatar())
                && StringUtil.notBlank(updateUserRequest.getAvatar())) {
            user.setAvatar(updateUserRequest.getAvatar());
        }
        if (!Objects.equals(updateUserRequest.getMotto(), user.getMotto())
                && StringUtil.notBlank(updateUserRequest.getMotto())) {
            user.setMotto(updateUserRequest.getMotto());
        }
        if (!Objects.equals(updateUserRequest.getNickname(), user.getNickname())
                && StringUtil.notBlank(updateUserRequest.getNickname())) {
            user.setNickname(updateUserRequest.getNickname());
        }
        if (!Objects.equals(updateUserRequest.getGrade(), user.getGrade())
                && StringUtil.notBlank(updateUserRequest.getGrade())) {
            user.setGrade(updateUserRequest.getGrade());
        }
        if (!Objects.equals(updateUserRequest.getName(), user.getName())
                && StringUtil.notBlank(updateUserRequest.getName())) {
            user.setName(updateUserRequest.getName());
        }

        userMapper.updateById(user);

        return Result.success(user);

        // FIXME: 不要返回密码！
    }
}

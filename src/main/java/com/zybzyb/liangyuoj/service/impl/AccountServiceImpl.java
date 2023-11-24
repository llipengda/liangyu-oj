package com.zybzyb.liangyuoj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import jakarta.mail.MessagingException;

import com.zybzyb.liangyuoj.common.CommonErrorCode;
import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.controller.request.LoginRequest;
import com.zybzyb.liangyuoj.controller.request.SignUpRequest;
import com.zybzyb.liangyuoj.controller.response.LoginResponse;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import com.zybzyb.liangyuoj.service.AccountService;
import com.zybzyb.liangyuoj.util.EmailUtil;
import com.zybzyb.liangyuoj.util.JWTUtil;
import com.zybzyb.liangyuoj.util.PasswordUtil;


@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public void signUp(SignUpRequest signUpRequest) throws CommonException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", signUpRequest.getEmail());
        if (userMapper.selectCount(userQueryWrapper) > 0) {
            throw new CommonException(CommonErrorCode.EMAIL_HAS_BEEN_SIGNED_UP);
        }
        User user = User.builder()
            .createTime(new Date())
            .email(signUpRequest.getEmail())
            .type(2)
            .password(PasswordUtil.hashPassword(signUpRequest.getPassword()))
            .nickname("用户" + signUpRequest.getEmail()
                .replaceAll("@.*", ""))
            .submitted(0)
            .solved(0)
            .build();
        userMapper.insert(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws CommonException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", email).isNull("delete_time");

        User user = userMapper.selectOne(userQueryWrapper);
        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            throw new CommonException(CommonErrorCode.DATA_ERROR);
        }
        return LoginResponse.builder()
            .userId(user.getId())
            .type(user.getType())
            .token(JWTUtil.createJwtToken(user))
            .build();
    }

    @Override
    public User updatePassword(String oldPassword, String newPassword, Long userId) throws CommonException {
        User user = userMapper.selectById(userId);
        if (!Objects.equals(user.getPassword(), PasswordUtil.hashPassword(oldPassword))) {
            throw new CommonException(CommonErrorCode.PASSWORD_SAME);
        }
        user.setPassword(PasswordUtil.hashPassword(newPassword));
        userMapper.updateById(user);
        user.setPassword(null);
        return user;
    }

    @Override
    public Boolean delete(Long userId) {
        User user = userMapper.selectById(userId);
        user.setDeleteTime(new Date());
        return userMapper.updateById(user) == 1;
    }

    @Override
    public void sendVerifyCode(String email) throws CommonException {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        if (userMapper.selectByMap(map)
            .size() != 0) {
            throw new CommonException(CommonErrorCode.EMAIL_HAS_BEEN_SIGNED_UP);
        }
        try {
            emailUtil.sendVerifyEmail(email);
        } catch (MessagingException e) {
            log.error("发送邮件失败", e);
            throw new CommonException(CommonErrorCode.SEND_EMAIL_FAILED);
        }
    }

    @Override
    public void sendConfirmEmail(String email) throws CommonException {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        if (userMapper.selectByMap(map)
            .size() == 0) {
            throw new CommonException(CommonErrorCode.USER_NOT_FOUND);
        }
        try {
            emailUtil.sendConfirmEmail(email);
        } catch (MessagingException e) {
            log.error("发送邮件失败", e);
            throw new CommonException(CommonErrorCode.SEND_EMAIL_FAILED);
        }
    }

    @Override
    public Boolean verifyCode(String email, String code) throws CommonException {
        return emailUtil.verify(email, code);
    }
}

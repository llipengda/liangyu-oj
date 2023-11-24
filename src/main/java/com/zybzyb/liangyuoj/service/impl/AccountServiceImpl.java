package com.zybzyb.liangyuoj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.zybzyb.liangyuoj.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    JavaMailSender jms;

    @Override
    public void signUp(SignUpRequest signUpRequest) throws CommonException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email", signUpRequest.getEmail());
        if (userMapper.selectCount(userQueryWrapper) > 0) {
            throw new CommonException(CommonErrorCode.EMAIL_ALREADY_EXIST);
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
    public void sendCode(String email) throws CommonException {
        if (redisUtil.isExpire(email)) {
            redisUtil.del(email);
        }
        String verificationCode = EmailUtil.getRandomVerifyCode();
        redisUtil.set(email, verificationCode, 900);
        try {
            EmailUtil.sendMail(sender, email, verificationCode, jms);
        } catch (Exception e) {
            log.error("发送邮件失败", e);
            throw new CommonException(CommonErrorCode.SEND_EMAIL_FAILED);
        }
    }

    @Override
    public Boolean verifyCode(String email, String code) throws CommonException {
        if (redisUtil.isExpire(email)) {
            throw new CommonException(CommonErrorCode.VERIFICATION_CODE_HAS_EXPIRED);
        }
        return redisUtil.get(email).equals(code);
    }
}

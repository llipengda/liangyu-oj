package com.zybzyb.liangyuoj.service;

import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.controller.request.LoginRequest;
import com.zybzyb.liangyuoj.controller.request.SignUpRequest;
import com.zybzyb.liangyuoj.controller.response.LoginResponse;
import com.zybzyb.liangyuoj.entity.User;


public interface AccountService {

    void signUp(SignUpRequest signUpRequest) throws CommonException;

    LoginResponse login(LoginRequest loginRequest) throws CommonException;

    User updatePassword(String email, String newPassword) throws CommonException;

    Boolean delete(Long userId);

    Boolean sendVerifyCode(String email) throws CommonException;

    Boolean sendConfirmEmail(String email) throws CommonException;

    Boolean verifyCode(String email, String code) throws CommonException;

    Boolean checkName(String name) throws CommonException;

    Boolean checkEmail(String email) throws CommonException;

}
package com.zybzyb.liangyuoj.service;

import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.controller.request.LoginRequest;
import com.zybzyb.liangyuoj.controller.request.SignUpRequest;
import com.zybzyb.liangyuoj.controller.response.LoginResponse;
import com.zybzyb.liangyuoj.entity.User;


public interface AccountService {

    void signUp(SignUpRequest signUpRequest) throws CommonException;

    LoginResponse login(LoginRequest loginRequest) throws CommonException;

    User updatePassword(String oldPassword, String newPassword, Long userId) throws CommonException;

    boolean delete(Long userId);

}
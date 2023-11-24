package com.zybzyb.liangyuoj.service;

import com.zybzyb.liangyuoj.controller.request.UpdateUserRequest;
import com.zybzyb.liangyuoj.entity.User;


public interface UserService {

    User getUserInfo(Long id);

    User updateUser(UpdateUserRequest updateUserRequest, Long userId) throws Exception;

}
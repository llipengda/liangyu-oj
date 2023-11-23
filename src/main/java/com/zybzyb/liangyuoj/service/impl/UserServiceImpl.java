package com.zybzyb.liangyuoj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zybzyb.liangyuoj.controller.request.UpdateUserRequest;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import com.zybzyb.liangyuoj.service.UserService;
import com.zybzyb.liangyuoj.util.ReflectUtil;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserInfo(Long id) {
        User user = userMapper.selectById(id);
        user.setPassword(null);
        return user;
    }

    @Override
    public User updateUser(UpdateUserRequest updateUserRequest, Long userId) throws Exception {
        User user = userMapper.selectById(userId);
        ReflectUtil.update(user, updateUserRequest);
        userMapper.updateById(user);
        user.setPassword(null);
        return user;
    }
}

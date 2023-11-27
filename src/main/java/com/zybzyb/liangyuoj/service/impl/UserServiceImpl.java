package com.zybzyb.liangyuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zybzyb.liangyuoj.entity.Submission;
import com.zybzyb.liangyuoj.mapper.SubmissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zybzyb.liangyuoj.common.CommonErrorCode;
import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.controller.request.UpdateUserRequest;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import com.zybzyb.liangyuoj.service.UserService;
import com.zybzyb.liangyuoj.util.AssertUtil;
import com.zybzyb.liangyuoj.util.ReflectUtil;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Value("${path.web}")
    private String web;

    @Value("${path.local}")
    private String local;

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

    @Override
    public String uploadImage(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        AssertUtil.notBlank(fileName, CommonErrorCode.FILE_NAME_EMPTY, null);
        if (fileName == null) {
            return null;
        }
        String extension;
        if (fileName.endsWith(".jpg")) {
            extension = ".jpg";
        } else if (fileName.endsWith(".png")) {
            extension = ".png";
        } else if (fileName.endsWith(".jpeg")) {
            extension = ".jpeg";
        } else if (fileName.endsWith(".gif")) {
            extension = ".gif";
        } else if (fileName.endsWith(".bmp")) {
            extension = ".bmp";
        } else if (fileName.endsWith(".webp")) {
            extension = ".webp";
        } else if (fileName.endsWith(".ico")) {
            extension = ".ico";
        } else if (fileName.endsWith(".tif")) {
            extension = ".tif";
        } else if (fileName.endsWith(".tiff")) {
            extension = ".tiff";
        } else if (fileName.endsWith(".svg")) {
            extension = ".svg";
        } else {
            throw new CommonException(CommonErrorCode.NOT_A_PICTURE);
        }
        String flag = UUID.randomUUID()
            .toString();
        String rootFilePath = local + flag + extension;
        Files.write(Path.of(rootFilePath), file.getBytes());
        return web + flag + extension;
    }

    @Override
    public List<Submission> getRecentSubmission(Long id) {
        QueryWrapper<Submission> submissionQueryWrapper = new QueryWrapper<>();
        submissionQueryWrapper.eq("user_id", id);
        submissionQueryWrapper.orderByDesc("submit_time");
        return submissionMapper.selectPage(new Page<>(1, 10), submissionQueryWrapper)
            .getRecords();
    }
}


package com.zybzyb.liangyuoj.service;

import com.zybzyb.liangyuoj.controller.request.UpdateUserRequest;
import com.zybzyb.liangyuoj.entity.Submission;
import com.zybzyb.liangyuoj.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {

    User getUserInfo(Long id);

    User updateUser(UpdateUserRequest updateUserRequest, Long userId) throws Exception;

    String uploadImage(MultipartFile file) throws Exception;

    List<Submission> getRecentSubmission(Long id);
}
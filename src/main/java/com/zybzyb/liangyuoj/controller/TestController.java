package com.zybzyb.liangyuoj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zybzyb.liangyuoj.annotation.NoAuth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@Tag(name = "测试接口", description = "测试接口")
@RequestMapping("/test")
public class TestController {

    @NoAuth
    @GetMapping("/200")
    public void test200(HttpServletResponse response) {
        response.setStatus(200);
    }

    @NoAuth
    @GetMapping("/400")
    public void test400(HttpServletResponse response) {
        response.setStatus(400);
    }

    @NoAuth
    @GetMapping("/401")
    public void test401(HttpServletResponse response) {
        response.setStatus(401);
    }

    @NoAuth
    @GetMapping("/403")
    public void test403(HttpServletResponse response) {
        response.setStatus(403);
    }

    @NoAuth
    @GetMapping("/404")
    public void test404(HttpServletResponse response) {
        response.setStatus(404);
    }

    @NoAuth
    @GetMapping("/500")
    public void test500(HttpServletResponse response) {
        response.setStatus(500);
    }

    @NoAuth
    @GetMapping("/502")
    public void test502(HttpServletResponse response) {
        response.setStatus(502);
    }
}

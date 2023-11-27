package com.zybzyb.liangyuoj.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zybzyb.liangyuoj.annotation.NoAuth;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@Hidden
public class RootController {
    
    @NoAuth
    @GetMapping("/")
    public void root(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/index.html");
    }
}

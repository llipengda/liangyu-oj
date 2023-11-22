package com.zybzyb.liangyuoj.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zybzyb.liangyuoj.entity.User;
import com.zybzyb.liangyuoj.util.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "hello2");
            return false;
        }
        try {
            token = token.replace("Bearer ", "");
            final Claims claims = JWTUtil.getClaimsFromJwt(token);
            if (claims == null || claims.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "hello2");
                return false;
            }
            User user = JSONObject.toJavaObject(JSONObject.parseObject((String) claims.get("user")), User.class);
            request.getSession().setAttribute("user", user);
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return false;
        }
    }
}

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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader("Authorization");
        if (token == null || token.equals("")) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return false;
        }
        try{
            final Claims claims = JWTUtil.getClaimsFromJwt(token);
            if(claims == null || claims.isEmpty()){
                response.sendRedirect(request.getContextPath() + "/user/login");
                return false;
            }
            User user = JSONObject.toJavaObject(JSONObject.parseObject((String) claims.get("user")), User.class);
            request.getSession().setAttribute("user", user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

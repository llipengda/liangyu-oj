package com.zybzyb.liangyuoj.interceptor;

import com.zybzyb.liangyuoj.annotation.NoAuth;
import com.zybzyb.liangyuoj.annotation.Role;
import com.zybzyb.liangyuoj.entity.JWTUser;
import com.zybzyb.liangyuoj.util.JWTUtil;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器
 *
 * @author xw, pdli
 * @version 2023/11/23
 * @see com.zybzyb.liangyuoj.annotation.NoAuth
 * @see com.zybzyb.liangyuoj.annotation.Role
 */
@NonNullApi
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        if (handlerMethod.getMethodAnnotation(NoAuth.class) != null) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try {
            token = token.replace("Bearer ", "");
            JWTUser user = JWTUtil.getUserFromJwt(token);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            Role role = handlerMethod.getMethodAnnotation(Role.class);
            if (role != null && role.type() < user.getType()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            request.getSession().setAttribute("user", user);
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
    }
}

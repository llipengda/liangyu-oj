package com.zybzyb.liangyuoj.util;

import com.alibaba.fastjson.JSONObject;
import com.zybzyb.liangyuoj.entity.JWTUser;
import com.zybzyb.liangyuoj.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;

import java.util.*;

/**
 * JWT工具类
 * @author xw,pdli
 * @version 2023/11/22
 */
public class JWTUtil {

    private final static String SECRET = "bml1bW9jaG91Ymlubm1zbGZ4eGt3b2NoYW5zaGluaWRlbWVuZw==";

    /**
     * 创建jwt
     */
    public static String createJwtToken(User user) {
        System.out.println(user);
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .claim("user", new JWTUser(user))
                .subject("app")
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SIG.HS256)
                .compact();
    }

    /**
     * 解密jwt
     */
    public static Claims getClaimsFromJwt(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static JWTUser getUserFromJwt(String token) {
        Claims claims = getClaimsFromJwt(token);
        if (claims == null) {
            return null;
        }
        return JSONObject.toJavaObject(
                JSONObject.parseObject(
                        JSONObject.toJSONString(claims.get("user"))),
                JWTUser.class);
    }
}

package com.zybzyb.liangyuoj.util;

import com.alibaba.fastjson.JSONObject;
import com.zybzyb.liangyuoj.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

public class JWTUtil {

    private final static String SECRET = "niumochoubin";

    /**
     * 创建jwt
     */
    public static String createJwtToken(User user){
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Map<String,Object> payload = new HashMap<>();
        payload.put("user", JSONObject.toJSONString(user));

        return Jwts.builder()
                .setHeader(map)
                .setClaims(payload)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .setSubject("app")
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    /**
     * 解密jwt
     */
    public static Claims getClaimsFromJwt(String jwt) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return claims;
    }

    public static void main(String[] args) {
        String jwtToken = createJwtToken(User.builder().nickname("shit").build());
        System.out.println(jwtToken);
        System.out.println();
        Claims claims = getClaimsFromJwt(jwtToken);
        System.out.println(claims);

        User user = JSONObject.toJavaObject(JSONObject.parseObject((String) claims.get("user")), User.class);
        System.out.println(user);
    }
}




package com.zybzyb.liangyuoj.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String password) {
        // 生成盐值，工作因子默认为10
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    // 验证密码
    public static boolean checkPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }
}

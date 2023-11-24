package com.zybzyb.liangyuoj.util;

/**
 * 字符串工具类
 * @author pdli
 * @version 2023/11/23
 */
public class StringUtil {
    public static boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }
}

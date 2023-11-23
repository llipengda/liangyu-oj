package com.zybzyb.liangyuoj.util;

public class StringUtil {
    public static boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }
}

package com.zybzyb.liangyuoj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static String getClassName(String code) {
        Matcher matcher = Pattern.compile("public\\s+class\\s+(\\w+)\\s*(<\\w.*>)*\\s*\\{")
            .matcher(code);
        List<String> res = new ArrayList<>();
        while (matcher.find()) {
            res.add(matcher.group(1));
        }
        return res.size() == 1 ? res.get(0) : null;
    }

    public static String replaceClassName(String code, String newClassName) {
        String className = getClassName(code);
        if (className == null) {
            return null;
        }
        return code.replaceAll(
            "([;\\},<>^\\s\\)\\{\\(])" + className + "([\\s+\\(\\)<,>\\{\\.:])",
            "$1" + newClassName + "$2"
        );
    }
}

package com.zybzyb.liangyuoj.util;

import java.util.ArrayList;
import java.util.List;

import com.google.re2j.Matcher;
import com.google.re2j.Pattern;

/**
 * 正则表达式工具类
 * @author pdli
 * @version 2023/11/24
 */
public class RegexUtil {
    public static String getClassName(String code) {
        Matcher matcher = Pattern.compile("public\\s+class\\s+(\\w+)\\s*(<.*>)?")
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
        className = Pattern.quote(className);
        return code.replaceAll(
            "([;},<>^\\s){(])" + className + "([\\s+()<,>{.:])",
            "$1" + newClassName + "$2"
        );
    }
}

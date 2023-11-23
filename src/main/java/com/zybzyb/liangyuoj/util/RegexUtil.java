package com.zybzyb.liangyuoj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegexUtil {
    public static String getClassName(String code) {
        Matcher matcher = Pattern.compile("public\\s+class\\s+(\\w+)\\s*\\{").matcher(code);
        List<String> res = new ArrayList<>();
        while(matcher.find()){
            res.add(matcher.group(1));
        }
        return res.size() == 1 ? res.get(0) : null;
    }

    public static void main(String[] args) {
        String code = "class B{} public class ami { class A{} public static void main(String[] args) { System.out.println(\"Hello, World!\"); } } class AAA{} class qqq{} class niumo{}";
        System.out.println(getClassName(code));
    }
}

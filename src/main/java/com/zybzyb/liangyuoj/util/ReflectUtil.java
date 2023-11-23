package com.zybzyb.liangyuoj.util;

import com.zybzyb.liangyuoj.controller.request.AddProblemRequest;
import com.zybzyb.liangyuoj.entity.Problem;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 反射工具类
 * @author xw
 * @version 2023/11/23
 */
public class ReflectUtil {

    public static void add(Object var0, Object var1) throws Exception {
        for (var var2 : var1.getClass().getDeclaredFields()){
            var2.setAccessible(true);
            if (var2.get(var1) != null) {
                Field var3 = var0.getClass().getDeclaredField(var2.getName());
                var3.setAccessible(true);
                var3.set(var0, var2.get(var1));
            }
        }
    }

    public static void update(Object var0, Object var1) throws Exception {
        for (var var2 : var1.getClass().getDeclaredFields()){
            var2.setAccessible(true);
            if (var2.get(var1) != null) {
                if(StringUtil.isBlank((String)var2.get(var1))){
                    continue;
                }
                Field var3 = var0.getClass().getDeclaredField(var2.getName());
                var3.setAccessible(true);
                if(!Objects.equals(var2.get(var1),var3.get(var0))){
                    var3.set(var0, var2.get(var1));
                }
            }
        }
    }

    public static void main(String[] args) {

    }

}

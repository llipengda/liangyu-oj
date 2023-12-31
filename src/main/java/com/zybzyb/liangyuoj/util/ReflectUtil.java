package com.zybzyb.liangyuoj.util;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 反射工具类
 * 
 * @author xw
 * @version 2023/11/23
 */
public class ReflectUtil {

    public static void add(Object var0, Object var1) throws Exception {
        for (var var2 : var1.getClass().getDeclaredFields()) {
            var2.setAccessible(true);
            if (var2.get(var1) != null) {
                Field var3 = var0.getClass().getDeclaredField(var2.getName());
                var3.setAccessible(true);
                if(!var2.getType().equals(var3.getType())){
                    continue;
                }
                if(var3.get(var0) == null) {
                    var3.set(var0, var2.get(var1));
                }
            }
        }
    }

    public static void update(Object var0, Object var1) throws Exception {
        for (var var2 : var1.getClass().getDeclaredFields()) {
            var2.setAccessible(true);
            if (var2.get(var1) != null) {
                if (var2.get(var1) instanceof String var3) {
                    if (StringUtil.isBlank(var3)) {
                        continue;
                    }
                }
                Field var4 = var0.getClass().getDeclaredField(var2.getName());
                var4.setAccessible(true);
                if(!var2.getType().equals(var4.getType())){
                    continue;
                }
                if (!Objects.equals(var2.get(var1), var4.get(var0))) {
                    var4.set(var0, var2.get(var1));
                }
            }
        }
    }
}

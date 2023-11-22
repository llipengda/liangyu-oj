package com.zybzyb.liangyuoj.util;

import com.zybzyb.liangyuoj.common.CommonErrorCode;
import com.zybzyb.liangyuoj.common.exception.CommonException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class AssertUtil {

    public AssertUtil() {
    }

    public static void isTrue(boolean expValue, CommonErrorCode resultCode, Object obj) {
        if (!expValue) {
            if (obj instanceof String) {
                throw new CommonException(resultCode, obj.toString());
            }
        }
    }

    public static void isTrue(boolean expValue, CommonErrorCode resultCode) {
        if (!expValue) {
            throw new CommonException(resultCode);
        }
    }

    public static void isFalse(boolean expValue, CommonErrorCode resultCode, Object obj) {
        isTrue(!expValue, resultCode, obj);
    }

    public static void isFalse(boolean expValue, CommonErrorCode resultCode) {
        isTrue(!expValue, resultCode);
    }

    public static void equals(Object obj1, Object obj2, CommonErrorCode resultCode, Object obj) {
        isTrue(Objects.equals(obj1, obj2), resultCode, obj);
    }

    public static void notEquals(Object obj1, Object obj2, CommonErrorCode resultCode, Object obj) {
        isTrue(!Objects.equals(obj1, obj2), resultCode, obj);
    }

    public static void contains(Object base, Collection<?> collection, CommonErrorCode resultCode, Object obj) {
        notEmpty(collection, resultCode, obj);
        isTrue(collection.contains(base), resultCode, obj);
    }

    public static void contains(Object base, Collection<?> collection, CommonErrorCode resultCode) {
        notEmpty(collection, resultCode);
        isTrue(collection.contains(base), resultCode);
    }

    public static void in(Object base, Object[] collection, CommonErrorCode resultCode, Object obj) {
        notNull(collection, resultCode, obj);
        boolean hasEqual = false;
        for (Object obj2 : collection) {
            if (base == obj2) {
                hasEqual = true;
                break;
            }
        }
        isTrue(hasEqual, resultCode, obj);
    }

    public static void notIn(Object base, Object[] collection, CommonErrorCode resultCode, Object obj) {
        if (null != collection) {
            for (Object obj2 : collection) {
                isTrue(base != obj2, resultCode, obj);
            }
        }
    }

    public static void blank(String str, CommonErrorCode resultCode, Object obj) {
        isTrue(isBlank(str), resultCode, obj);
    }

    public static void notBlank(String str, CommonErrorCode resultCode, Object obj) {
        isTrue(!isBlank(str), resultCode, obj);
    }

    public static void isNull(Object object, CommonErrorCode resultCode, Object obj) {
        isTrue(object == null, resultCode, obj);
    }

    public static void notNull(Object object, CommonErrorCode resultCode, Object obj) {
        isTrue(object != null, resultCode, obj);
    }

    public static void notNull(Object object, CommonErrorCode resultCode) {
        isTrue(object != null, resultCode, null);
    }

    public static void isNull(Object object, CommonErrorCode resultCode) {
        isTrue(object == null, resultCode, null);
    }

    public static void notEmpty(Collection<?> collection, CommonErrorCode resultCode, Object obj) {
        isTrue(!CollectionUtils.isEmpty(collection), resultCode, obj);
    }

    public static void notEmpty(Collection<?> collection, CommonErrorCode resultCode) {
        isTrue(!CollectionUtils.isEmpty(collection), resultCode);
    }

    public static void empty(Collection<?> collection, CommonErrorCode resultCode, Object obj) {
        isTrue(CollectionUtils.isEmpty(collection), resultCode, obj);
    }

    public static void notEmpty(Map<?, ?> map, CommonErrorCode resultCode, Object obj) {
        isTrue(!CollectionUtils.isEmpty(map), resultCode, obj);
    }

    public static void empty(Map<?, ?> map, CommonErrorCode resultCode, Object obj) {
        isTrue(CollectionUtils.isEmpty(map), resultCode, obj);
    }

    private static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}

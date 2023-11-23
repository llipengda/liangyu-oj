package com.zybzyb.liangyuoj.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RegexUtilTest {
    @Test
    void getClassNameOk1() {
        String code = "public class MyClass { }";
        String expected = "MyClass";
        String actual = RegexUtil.getClassName(code);
        assertEquals(expected, actual);
    }

    @Test
    void getClassNameOk2() {
        String code = """
            class NotPublicClass { }
            public class MyClass { }
            class AnotherNotPublicClass { }
            """;
        String expected = "MyClass";
        String actual = RegexUtil.getClassName(code);
        assertEquals(expected, actual);
    }

    @Test
    void getClassNameFailed1() {
        String code = "class MyClass { }";
        String actual = RegexUtil.getClassName(code);
        assertEquals(null, actual);
    }

    @Test
    void getClassNameFailed2() {
        String code = """
            class NotPublicClass { }
            public class MyClass { }
            class AnotherNotPublicClass { }
            public class AnotherMyClass { }
            """;
        String actual = RegexUtil.getClassName(code);
        assertEquals(null, actual);
    }
}
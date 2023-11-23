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
    void getClassNameGeneric() {
        String code = """
            public class MyClass<T> {
            }
            class OtherMyClass<T> { }
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

    @Test
    void replaceClassNameSimple() {
        String code = "public class MyClass { }";
        String newClassName = "NewClass";
        String expected = "public class QNewClassE { }";
        String actual = RegexUtil.replaceClassName(code, newClassName);
        assertEquals(expected, actual);
    }

    @Test
    void replaceClassNameComplex() {
        String code = """
            public class MyClass {
                public static void main(String[] args) {
                    MyClass myClass = new MyClass();
                    System.out.println(myClass);
                    MyClass myClass2 = new MyClass ();
                    System.out.println(myClass2);MyClass myClass3 = new MyClass();
                    if (true)MyClass myClass4 = new MyClass();
                    if (false) {MyClass myClass5 = new MyClass();
                        if (myclass5 instanceof MyClass) {
                        }
                    }
                    OtherMyClass otherMyClass = new OtherMyClass();
                }
            }
            class OtherMyClass { }
            """;
        String newClassName = "NewClass";
        String expected = """
            public class QNewClassE {
                public static void main(String[] args) {
                    QNewClassE myClass = new QNewClassE();
                    System.out.println(myClass);
                    QNewClassE myClass2 = new QNewClassE ();
                    System.out.println(myClass2);QNewClassE myClass3 = new QNewClassE();
                    if (true)QNewClassE myClass4 = new QNewClassE();
                    if (false) {QNewClassE myClass5 = new QNewClassE();
                        if (myclass5 instanceof QNewClassE) {
                        }
                    }
                    OtherMyClass otherMyClass = new OtherMyClass();
                }
            }
            class OtherMyClass { }
            """;
        String actual = RegexUtil.replaceClassName(code, newClassName);
        assertEquals(expected, actual);
    }

    @Test
    void replaceClassNameGeneric() {
        String code = """
            public class MyClass<T> {
                public static void main(String[] args) {
                    MyClass<String> myClass = new MyClass<String>();
                    System.out.println(myClass);
                    MyClass<String> myClass2 = new MyClass <String> ();
                    System.out.println(myClass2);MyClass<String> myClass3 = new MyClass<String>();
                    if (true)MyClass<String> myClass4 = new MyClass<String>();
                    if (false) {MyClass<String> myClass5 = new MyClass<String>();
                        if (myclass5 instanceof MyClass<String>) {
                        }
                    }
                    OtherMyClass<String> otherMyClass = new OtherMyClass<String>();
                    List<MyClass<String>> list = new ArrayList< MyClass<String>>();
                    Map<String,MyClass<String>> map = new HashMap<String, MyClass<String>>();
                    Map<MyClass<String>,String> map2 = new HashMap<MyClass<String>, String>();
                }
            }
            class OtherMyClass<T> { }
            """;
        String newClassName = "NewClass";
        String expected = """
            public class QNewClassE<T> {
                public static void main(String[] args) {
                    QNewClassE<String> myClass = new QNewClassE<String>();
                    System.out.println(myClass);
                    QNewClassE<String> myClass2 = new QNewClassE <String> ();
                    System.out.println(myClass2);QNewClassE<String> myClass3 = new QNewClassE<String>();
                    if (true)QNewClassE<String> myClass4 = new QNewClassE<String>();
                    if (false) {QNewClassE<String> myClass5 = new QNewClassE<String>();
                        if (myclass5 instanceof QNewClassE<String>) {
                        }
                    }
                    OtherMyClass<String> otherMyClass = new OtherMyClass<String>();
                    List<QNewClassE<String>> list = new ArrayList< QNewClassE<String>>();
                    Map<String,QNewClassE<String>> map = new HashMap<String, QNewClassE<String>>();
                    Map<QNewClassE<String>,String> map2 = new HashMap<QNewClassE<String>, String>();
                }
            }
            class OtherMyClass<T> { }
            """;
        String actual = RegexUtil.replaceClassName(code, newClassName);
        assertEquals(expected, actual);
    }

    @Test
    void replaceClassNameReflectionAndStatic() {
        String code = """
            public class MyClass {
                public static void main(String[] args) {
                    Class<?> myClass = MyClass.class;
                    System.out.println(myClass);
                    Class<?> myClass2 = MyClass .class;
                    MyClass.myMethod();
                }
                public static void myMethod() {
                    Class<?> myClass3 = MyClass.class;
                }
            }
            """;
        String newClassName = "NewClass";
        String expected = """
            public class QNewClassE {
                public static void main(String[] args) {
                    Class<?> myClass = QNewClassE.class;
                    System.out.println(myClass);
                    Class<?> myClass2 = QNewClassE .class;
                    QNewClassE.myMethod();
                }
                public static void myMethod() {
                    Class<?> myClass3 = QNewClassE.class;
                }
            }
            """;
        String actual = RegexUtil.replaceClassName(code, newClassName);
        assertEquals(expected, actual);
    }

    @Test
    void replaceClassNameDoubleColon() {
        String code = """
            public class MyClass {
                public static void main(String[] args) {
                    List<MyClass> list = new ArrayList<MyClass>();
                    list.forEach(MyClass::myMethod);
                }
                public void myMethod() {
                }
            }
            class OtherMyClass {
                public static void main(String[] args) {
                    List<OtherMyClass> list = new ArrayList<OtherMyClass>();
                    list.forEach(OtherMyClass::myMethod);
                }
                public void myMethod() {
                }
            }
            """;
        String newClassName = "NewClass";
        String expected = """
            public class QNewClassE {
                public static void main(String[] args) {
                    List<QNewClassE> list = new ArrayList<QNewClassE>();
                    list.forEach(QNewClassE::myMethod);
                }
                public void myMethod() {
                }
            }
            class OtherMyClass {
                public static void main(String[] args) {
                    List<OtherMyClass> list = new ArrayList<OtherMyClass>();
                    list.forEach(OtherMyClass::myMethod);
                }
                public void myMethod() {
                }
            }
            """;
        String actual = RegexUtil.replaceClassName(code, newClassName);
        assertEquals(expected, actual);
    }

    @Test
    void replaceClassNameFailed1() {
        String code = "class MyClass { }";
        String newClassName = "NewClass";
        String actual = RegexUtil.replaceClassName(code, newClassName);
        assertEquals(null, actual);
    }

    @Test
    void replaceClassNameFailed2() {
        String code = """
            class NotPublicClass { }
            public class MyClass { }
            class AnotherNotPublicClass { }
            public class AnotherMyClass { }
            """;
        String newClassName = "NewClass";
        String actual = RegexUtil.replaceClassName(code, newClassName);
        assertEquals(null, actual);
    }
}
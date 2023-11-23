package com.zybzyb.liangyuoj.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
        String expected = "public class NewClass { }";
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
            public class NewClass {
                public static void main(String[] args) {
                    NewClass myClass = new NewClass();
                    System.out.println(myClass);
                    NewClass myClass2 = new NewClass ();
                    System.out.println(myClass2);NewClass myClass3 = new NewClass();
                    if (true)NewClass myClass4 = new NewClass();
                    if (false) {NewClass myClass5 = new NewClass();
                        if (myclass5 instanceof NewClass) {
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
            public class NewClass<T> {
                public static void main(String[] args) {
                    NewClass<String> myClass = new NewClass<String>();
                    System.out.println(myClass);
                    NewClass<String> myClass2 = new NewClass <String> ();
                    System.out.println(myClass2);NewClass<String> myClass3 = new NewClass<String>();
                    if (true)NewClass<String> myClass4 = new NewClass<String>();
                    if (false) {NewClass<String> myClass5 = new NewClass<String>();
                        if (myclass5 instanceof NewClass<String>) {
                        }
                    }
                    OtherMyClass<String> otherMyClass = new OtherMyClass<String>();
                    List<NewClass<String>> list = new ArrayList< NewClass<String>>();
                    Map<String,NewClass<String>> map = new HashMap<String, NewClass<String>>();
                    Map<NewClass<String>,String> map2 = new HashMap<NewClass<String>, String>();
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
            public class NewClass {
                public static void main(String[] args) {
                    Class<?> myClass = NewClass.class;
                    System.out.println(myClass);
                    Class<?> myClass2 = NewClass .class;
                    NewClass.myMethod();
                }
                public static void myMethod() {
                    Class<?> myClass3 = NewClass.class;
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
            public class NewClass {
                public static void main(String[] args) {
                    List<NewClass> list = new ArrayList<NewClass>();
                    list.forEach(NewClass::myMethod);
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
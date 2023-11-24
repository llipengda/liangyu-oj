package com.zybzyb.liangyuoj.util;

import org.junit.jupiter.api.Test;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class EvaluateUtilTest {

    @Test
    void correctOutput() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, "",expectedOutput);
        System.out.println(res);
        assertEquals(EvaluateStatus.AC, res.getStatus());
    }

    @Test
    void wrongOutput() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello\"); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, "",expectedOutput);
        System.out.println(res);
        assertEquals(EvaluateStatus.WA, res.getStatus());
    }

    @Test
    void compilationError() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello, World!\") } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, "",expectedOutput);
        System.out.println(res);
        assertEquals(EvaluateStatus.CE, res.getStatus());
    }

    @Test
    void timeLimitExceeded() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { while (true); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, "",expectedOutput);
        System.out.println(res);
        assertEquals(EvaluateStatus.TLE, res.getStatus());
    }

    @Test
    void runtimeError() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { int x = 5 / 0; } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, "",expectedOutput);
        System.out.println(res);
        assertEquals(EvaluateStatus.RE, res.getStatus());
    }

    @Test
    void inputTest() throws Exception {
        String sourceCode = "import java.util.Scanner; public class Solution { public static void main(String[] args) { Scanner scanner = new Scanner(System.in); System.out.println(scanner.nextLine()); } }";
        String input = "Hello, World!";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, input,expectedOutput);
        System.out.println(res);
        assertEquals(EvaluateStatus.AC, res.getStatus());
    }

    public static void main(String[] args) {
        String javaCode = """
                public class MyClass {
                    public static void main(String[] args) {
                        System.out.println("Hello, World!");
                        System.out.println("Hello, World!");
                        System.out.println("Hello, World!");
                        System.out.println("Hello, World!");
                        // Main method content
                        System.out.println("Hello, World!");
                    }
                }""";

        Pattern pattern = Pattern.compile("public static void main\\s*\\([^)]*\\)\\s*\\{(?:[^}]*\\}\\s*)+([^}]*\\})\\s*$", Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(javaCode);

        if (matcher.find()) {
            String lastLineOfMain = matcher.group(1);
            System.out.println("Last Line of Main Method:\n" + lastLineOfMain);
        } else {
            System.out.println("Main method not found");
        }
    }
}

class MemoryUsageExample {
    public static void main(String[] args) {
        String javaCode = """
                public class MyClass {
                    public static void main(String[] args) {
                        // Main method content
                        System.out.println("Hello, World!");
                    }
                }""";

        Pattern pattern = Pattern.compile("(public static void main\\s*\\([^)]*\\)\\s*\\{(?:[^}]*\\}\\s*)+)([^}]*\\})\\s*$", Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(javaCode);

        if (matcher.find()) {
            // 获取匹配到的最后一行
            String lastLineOfMain = matcher.group(2);

            // 添加新语句
            String newStatement = "        System.out.println(\"New Statement!\");\n";

            // 替换最后一行
            String updatedJavaCode = javaCode.replace(lastLineOfMain, lastLineOfMain + newStatement);

            System.out.println("Updated Java Code:\n" + updatedJavaCode);
        } else {
            System.out.println("Main method not found");
        }
    }
}


package com.zybzyb.liangyuoj.util;

import org.junit.jupiter.api.Test;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import static org.junit.jupiter.api.Assertions.*;

class EvaluateUtilTest {

    @Test
    void correctOutput() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, "", expectedOutput,2,256);
        assertEquals(EvaluateStatus.AC, res.getStatus());
    }

    @Test
    void wrongOutput() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello\"); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, "", expectedOutput,2,256);
        assertEquals(EvaluateStatus.WA, res.getStatus());
    }

    @Test
    void compilationError() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello, World!\") } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, "", expectedOutput,2,256);
        assertEquals(EvaluateStatus.CE, res.getStatus());
    }

    @Test
    void timeLimitExceeded() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { while (true); } }";
        String expectedOutput = "";
        var res = EvaluateUtil.execute(sourceCode, "", expectedOutput,2,256);
        assertEquals(EvaluateStatus.TLE, res.getStatus());
    }

    @Test
    void memoryLimitExceeded() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { int[] a = new int[1024 * 1024 * 100]; } }";
        String expectedOutput = "";
        var res = EvaluateUtil.execute(sourceCode, "", expectedOutput,2,256);
        assertEquals(EvaluateStatus.MLE, res.getStatus());
    }

    @Test
    void runtimeError() throws Exception {
        String sourceCode = "public class Solution { public static void main(String[] args) { int x = 5 / 0; } }";
        String expectedOutput = "";
        var res = EvaluateUtil.execute(sourceCode, "", expectedOutput,2,256);
        assertEquals(EvaluateStatus.RE, res.getStatus());
    }

    @Test
    void inputTest() throws Exception {
        String sourceCode = """
            import java.util.Scanner;
            public class Solution {
                public static void main(String[] args) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println(scanner.nextLine());
                }
            }
            """;
        String input = "Hello, World!";
        String expectedOutput = "Hello, World!";
        var res = EvaluateUtil.execute(sourceCode, input, expectedOutput,2,256);
        assertEquals(EvaluateStatus.AC, res.getStatus());
    }

    @Test
    void invalidOpeartionTest() throws Exception {
        String sourceCode = """
            import java.io.File;
            import java.io.IOException;
            import java.nio.file.Files;

            public class Solution {
                public static void main(String[] args) throws IOException {
                    File file = new File("D:/test.txt");
                    Files.write(file.toPath(), "Hello, World!".getBytes());
                }
            }
            """;
        String expectedOutput = "";
        var res = EvaluateUtil.execute(sourceCode, "", expectedOutput,2,256);
        assertEquals(EvaluateStatus.RE, res.getStatus());
    }
}


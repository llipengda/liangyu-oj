package com.zybzyb.liangyuoj.util;

import org.junit.jupiter.api.Test;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }
}

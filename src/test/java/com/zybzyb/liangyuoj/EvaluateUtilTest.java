package com.zybzyb.liangyuoj;

import org.junit.jupiter.api.Test;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import com.zybzyb.liangyuoj.util.EvaluateUtil;

import static org.junit.jupiter.api.Assertions.*;

class EvaluateUtilTest {

    @Test
    void correctOutput() {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(EvaluateStatus.AC, res.getStatus());
    }

    @Test
    void wrongOutput() {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello\"); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(EvaluateStatus.WA, res.getStatus());
    }

    @Test
    void compilationError() {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello, World!\") } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(EvaluateStatus.CE, res.getStatus());
    }

    @Test
    void timeLimitExceeded() {
        String sourceCode = "public class Solution { public static void main(String[] args) { while (true); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(EvaluateStatus.TLE, res.getStatus());
    }

    @Test
    void runtimeError() {
        String sourceCode = "public class Solution { public static void main(String[] args) { int x = 5 / 0; } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(EvaluateStatus.RE, res.getStatus());
    }
}

package com.zybzyb.liangyuoj;

import org.junit.jupiter.api.Test;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import com.zybzyb.liangyuoj.util.EvaluateUtil;

import static org.junit.jupiter.api.Assertions.*;

class EvaluateUtilTest {

    @Test
    void testExecute_CorrectOutput() {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(res.getStatus(), EvaluateStatus.AC);
    }

    @Test
    void testExecute_WrongOutput() {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello\"); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(res.getStatus(), EvaluateStatus.WA);
    }

    @Test
    void testExecute_CompilationError() {
        String sourceCode = "public class Solution { public static void main(String[] args) { System.out.println(\"Hello, World!\") } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(res.getStatus(), EvaluateStatus.CE);
    }

    @Test
    void testExecute_TimeLimitExceeded() {
        String sourceCode = "public class Solution { public static void main(String[] args) { while (true); } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(res.getStatus(), EvaluateStatus.TLE);
    }

    @Test
    void testExecute_RuntimeError() {
        String sourceCode = "public class Solution { public static void main(String[] args) { int x = 5 / 0; } }";
        String expectedOutput = "Hello, World!\n";
        var res = EvaluateUtil.execute(sourceCode, expectedOutput);
        assertEquals(res.getStatus(), EvaluateStatus.RE);
    }
}
package com.zybzyb.liangyuoj.util;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import com.zybzyb.liangyuoj.entity.EvaluateResult;

/**
 * 评测工具类
 * TODO: MLE
 * 
 * @author xw, pdli, zyb
 * @version 2023/11/23
 */
public class EvaluateUtil {

    public static EvaluateResult execute(String sourceCode, String expectedOutput) throws Exception {
        String workDir = ".\\tests\\";
        if (!new File(workDir).exists()) {
            if (new File(workDir).mkdirs()) {
                System.out.println("create work directory success");
            } else {
                System.out.println("create work directory failed");
            }
        }

        String className = RegexUtil.getClassName(sourceCode);
        if (className == null) {
            return new EvaluateResult(EvaluateStatus.CE,
                "Compilation Error: \n" + "No public class found or too many public classes.", null);
        }
        String javaFileName = workDir + className + ".java";
        String classOutputPath = ".";


        try {
            // 保存为.java文件
            Files.write(Paths.get(javaFileName), sourceCode.getBytes());

            // 编译Java源代码
            ProcessBuilder compileBuilder = new ProcessBuilder("javac", javaFileName);
            compileBuilder.directory(new File(classOutputPath));
            Process compile = compileBuilder.start();

            // 编译如果超时了，就打印一个编译超时
            if (!compile.waitFor(10, TimeUnit.SECONDS)) {
                System.out.println("Compilation Time Limit Exceeded.");
                return new EvaluateResult(EvaluateStatus.CE, "Compilation Time Limit Exceeded.", 10.0);
            }

            // 编译如果失败了，就打印一个编译失败+原因
            if (compile.exitValue() != 0) {
                String errors = new String(compile.getErrorStream()
                    .readAllBytes());
                System.out.println("Compilation Error: \n" + errors);
                return new EvaluateResult(EvaluateStatus.CE, errors, null);
            }

            // 运行.class文件
            ProcessBuilder runBuilder = new ProcessBuilder("java", "-cp", workDir, className);
            long start = System.currentTimeMillis();
            Process run = runBuilder.start();

            // 设个计时器，如果超时了，就打印一个超时
            boolean finished = run.waitFor(2, TimeUnit.SECONDS);

            if (finished && run.exitValue() == 0) {
                long time = System.currentTimeMillis() - start;
                // 获取跑完之后的输出
                String output = new String(run.getInputStream()
                    .readAllBytes());
                // 判断输出正误
                if (output.trim()
                    .equals(expectedOutput.trim())) {
                    System.out.println("Correct Answer.");
                    run.destroy();
                    return new EvaluateResult(EvaluateStatus.AC, "评测通过", time / 1000.0);
                } else {
                    System.out.println("Wrong Answer.");
                    System.out.println("Expected Output: \n" + expectedOutput);
                    System.out.println("Program Output: \n" + output);
                    return new EvaluateResult(EvaluateStatus.WA, output, time / 1000.0);
                }
            } else if (!finished) {
                System.out.println("Time Limit Exceeded.");
                run.destroy();
                return new EvaluateResult(EvaluateStatus.TLE, "Time Limit Exceeded.", 2.0);
            } else if (run.exitValue() != 0) {
                System.out.println("Runtime Error.");
                String errors = new String(run.getErrorStream()
                    .readAllBytes());
                System.out.println("Program Output: \n" + errors);
                run.destroy();
                return new EvaluateResult(EvaluateStatus.RE, errors, null);
            }
            return new EvaluateResult(EvaluateStatus.RE, "评测失败", null);
        } finally {
            // 删除跑完之后的文件
            if (new File(javaFileName).exists()) {
                System.out.println("delete .java :" + new File(javaFileName).delete());
            }
            if (new File(javaFileName.replace(".java", "") + ".class").exists()) {
                System.out.println("delete .class :" + new File(javaFileName.replace(".java", "") + ".class").delete());
            }
        }
    }
}
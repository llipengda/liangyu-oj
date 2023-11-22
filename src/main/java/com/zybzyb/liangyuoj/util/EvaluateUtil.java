package com.zybzyb.liangyuoj.util;

import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import com.zybzyb.liangyuoj.entity.EvaluateResult;

/**
 * zyb可以看看这个，也可以直接把这个完善一下
 * 输入的代码应该是由别的地方传过来，然后要把运行结果返回给别的地方
 * 测试用例的输入也应该由别的地方传入
 * 也就是说，别的地方调用这里的一个方法，传入代码和测试用例的输入，然后这里需要返回运行的结果
 * TODO：还没对测试用例输入进行处理，也没统计运行时间，还有异常处理
 * TODO: 还要考虑输入的代码类名的问题，这里写死了
 * 
 * @author xw, pdli
 * @version 2023/11/19
 * 
 */
public class EvaluateUtil {

    public static EvaluateResult execute(String sourceCode, String expectedOutput) {
        String workDir = ".\\tests\\";
        if (!new File(workDir).exists()) {
            if (new File(workDir).mkdirs()) {
                System.out.println("create work directory success");
            } else {
                System.out.println("create work directory failed");
            }
        }

        String className = "Solution";
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
                return new EvaluateResult(EvaluateStatus.CE, "Compilation Time Limit Exceeded.");
            }

            // 编译如果失败了，就打印一个编译失败+原因
            if (compile.exitValue() != 0) {
                String errors = new String(compile.getErrorStream().readAllBytes());
                System.out.println("Compilation Error: \n" + errors);
                return new EvaluateResult(EvaluateStatus.CE, errors);
            }

            // 运行.class文件
            ProcessBuilder runBuilder = new ProcessBuilder("java", "-cp", workDir, className);
            Process run = runBuilder.start();

            // 设个计时器，如果超时了，就打印一个超时
            boolean finished = run.waitFor(1, TimeUnit.SECONDS);

            if (finished && run.exitValue() == 0) {
                // 获取跑完之后的输出
                String output = new String(run.getInputStream().readAllBytes());
                // 判断输出正误
                if (output.trim().equals(expectedOutput.trim())) {
                    System.out.println("Correct Answer.");
                    return new EvaluateResult(EvaluateStatus.AC, "评测通过");
                } else {
                    System.out.println("Wrong Answer.");
                    System.out.println("Expected Output: \n" + expectedOutput);
                    System.out.println("Program Output: \n" + output);
                    return new EvaluateResult(EvaluateStatus.WA, output);
                }
            } else if (!finished) {
                System.out.println("Time Limit Exceeded.");
                return new EvaluateResult(EvaluateStatus.TLE, "Time Limit Exceeded.");
            } else {
                System.out.println("Runtime Error.");
                String errors = new String(run.getErrorStream().readAllBytes());
                System.out.println("Program Output: \n" + errors);
                return new EvaluateResult(EvaluateStatus.RE, errors);
            }
        } catch (IOException | InterruptedException e) {
            // 这里先全都printStackTrace了
            e.printStackTrace(System.err);
        } finally {
            // 删除跑完之后的文件
            if (new File(javaFileName).exists()) {
                System.out.println("delete .java :" + new File(javaFileName).delete());
            }
            if (new File(javaFileName.replace(".java", "") + ".class").exists()) {
                System.out.println("delete .class :" + new File(javaFileName.replace(".java", "") + ".class").delete());
            }
        }
        return new EvaluateResult(EvaluateStatus.RE, "评测失败");
    }
}
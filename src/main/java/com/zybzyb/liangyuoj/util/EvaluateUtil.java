package com.zybzyb.liangyuoj.util;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.*;

import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import com.zybzyb.liangyuoj.entity.EvaluateResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 评测工具类
 * TODO: MLE
 * 
 * @author xw, pdli, zyb
 * @version 2023/11/23
 */
@Slf4j
public class EvaluateUtil {

    private static final long MEMORY_LIMIT = 256 * 1024 * 1024;

    public static EvaluateResult execute(String sourceCode, String input, String expectedOutput) throws Exception {
        String workDir = ".\\tests\\";
        if (!new File(workDir).exists()) {
            if (new File(workDir).mkdirs()) {
                System.out.println("create work directory success");
            } else {
                System.out.println("create work directory failed");
            }
        }

        String className = "Solution" + UUID.randomUUID()
            .toString()
            .replace("-", "");
        sourceCode = RegexUtil.replaceClassName(sourceCode, className);
        if (sourceCode == null) {
            return new EvaluateResult(
                EvaluateStatus.CE,
                "Compilation Error: \n" + "No public class found or too many public classes.",
                null
            );
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
                log.info("Compilation Time Limit Exceeded.");
                return new EvaluateResult(
                    EvaluateStatus.CE,
                    "Compilation Time Limit Exceeded.",
                    10.0
                );
            }

            // 编译如果失败了，就打印一个编译失败+原因
            if (compile.exitValue() != 0) {
                String errors = new String(compile.getErrorStream()
                    .readAllBytes());
                log.info("Compilation Error: \n" + errors);
                return new EvaluateResult(EvaluateStatus.CE, errors, null);
            }

            if(StringUtil.notBlank(input)){
                Files.write(Paths.get(workDir + "input.txt"), input.getBytes());
            }

            // 运行.class文件
            ProcessBuilder runBuilder = new ProcessBuilder("java",
                    "-cp",
                    workDir,
                    className,
                    "<",
                    workDir + "input.txt"
            );
            System.out.println(runBuilder.command());
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
                    log.info("Correct Answer.");
                    run.destroy();
                    return new EvaluateResult(EvaluateStatus.AC, "评测通过", time / 1000.0);
                } else {
                    log.info("Wrong Answer.");
                    log.info("Expected Output: \n" + expectedOutput);
                    log.info("Program Output: \n" + output);
                    return new EvaluateResult(EvaluateStatus.WA, output, time / 1000.0);
                }
            } else if (!finished) {
                log.info("Time Limit Exceeded.");
                run.destroy();
                return new EvaluateResult(EvaluateStatus.TLE, "Time Limit Exceeded.", 2.0);
            } else if (run.exitValue() != 0) {
                log.info("Runtime Error.");
                String errors = new String(run.getErrorStream()
                    .readAllBytes());
                log.info("Program Output: \n" + errors);
                run.destroy();
                return new EvaluateResult(EvaluateStatus.RE, errors, null);
            }
            return new EvaluateResult(EvaluateStatus.RE, "评测失败", null);
        } finally {
            // 删除跑完之后的文件
            if (new File(javaFileName).exists()) {
                log.info("delete .java :" + new File(javaFileName).delete());
            }
            if (new File(javaFileName.replace(".java", "") + ".class").exists()) {
                log.info("delete .class :" + new File(javaFileName.replace(".java", "") + ".class").delete());
            }
        }
    }
}
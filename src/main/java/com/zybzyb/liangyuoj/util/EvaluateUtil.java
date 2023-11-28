package com.zybzyb.liangyuoj.util;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.*;

import com.google.re2j.Matcher;
import com.google.re2j.Pattern;
import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import com.zybzyb.liangyuoj.entity.EvaluateResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 评测工具类
 * 
 * @author xw, pdli, zyb
 * @version 2023/11/23
 */
@Slf4j
public class EvaluateUtil implements Callable<EvaluateResult> {

    public static EvaluateResult execute(String sourceCode, String input, String expectedOutput) throws Exception {
        String workDir = "./tests/";
        assert new File(workDir).exists();

        String className = "Solution" + UUID.randomUUID()
            .toString()
            .replace("-", "");
        sourceCode = RegexUtil.replaceClassName(sourceCode, className);
        if (sourceCode == null) {
            return new EvaluateResult(
                EvaluateStatus.CE,
                "Compilation Error: \n" + "No public class found or too many public classes.",
                null,
                null
            );
        }
        String javaFileName = workDir + className + ".java";
        String classOutputPath = ".";

        String runnerFileName = workDir + className + "Runner.java";

        String runnerCode = """
            public class %s {
                public static void main(String[] args) throws Exception {
                    %s.main(args);
                    long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    System.out.println("%s-memory:" + memory / 1024 / 1024 + "M");
                }
            }
            """.formatted(className + "Runner", className, className);

        try {
            // 保存为.java文件
            Files.write(Paths.get(javaFileName), sourceCode.getBytes());
            // 保存为Runner.java文件
            Files.write(Paths.get(runnerFileName), runnerCode.getBytes());

            // 编译Java源代码
            ProcessBuilder compileBuilder = new ProcessBuilder("javac", javaFileName, runnerFileName);
            compileBuilder.directory(new File(classOutputPath));
            Process compile = compileBuilder.start();

            // 编译如果超时了，就打印一个编译超时
            if (!compile.waitFor(10, TimeUnit.SECONDS)) {
                log.info("Compilation Time Limit Exceeded.");
                return new EvaluateResult(
                    EvaluateStatus.CE,
                    "Compilation Time Limit Exceeded.",
                    10.0,
                    null
                );
            }

            // 编译如果失败了，就打印一个编译失败+原因
            if (compile.exitValue() != 0) {
                String errors = new String(compile.getErrorStream()
                    .readAllBytes());
                log.info("Compilation Error: \n" + errors);
                return new EvaluateResult(EvaluateStatus.CE, errors, null, null);
            }

            // 运行.class文件
            ProcessBuilder runBuilder = new ProcessBuilder("java",
                "-Djava.security.manager",
                "-Djava.security.policy=../policy/read-only.policy",
                "-cp",
                workDir,
                className + "Runner"
            );
            System.out.println(runBuilder.command());
            long start = System.currentTimeMillis();
            Process run = runBuilder.start();

            // 输入
            OutputStream stdin = run.getOutputStream();
            stdin.write(input.getBytes());
            stdin.flush();
            stdin.close();

            // 设个计时器，如果超时了，就打印一个超时
            boolean finished = run.waitFor(2, TimeUnit.SECONDS);

            if (finished && run.exitValue() == 0) {
                long time = System.currentTimeMillis() - start;
                // 获取跑完之后的输出
                String output = new String(run.getInputStream()
                    .readAllBytes());
                Pattern pattern = Pattern.compile(className + "-memory:(\\d+)M");
                Matcher matcher = pattern.matcher(output);
                Long memory = matcher.find() ? Long.parseLong(matcher.group(1)) : null;
                if (memory != null && memory > 256) {
                    log.info("Memory Limit Exceeded.");
                    run.destroy();
                    return new EvaluateResult(EvaluateStatus.MLE, "Memory Limit Exceeded.", time / 1000.0, memory);
                }
                output = output.replaceAll(className + "-memory:.+M$", "");
                output = output.replaceAll("\\s*\\n\\s*", "\n");
                // 判断输出正误
                if (output.trim()
                    .equals(expectedOutput.trim())) {
                    log.info("Correct Answer.");
                    run.destroy();
                    return new EvaluateResult(EvaluateStatus.AC, """
                            评测通过
                                输出：
                                %s
                            """.formatted(output), time / 1000.0, memory);
                } else {
                    log.info("Wrong Answer.");
                    log.info("Expected Output: \n" + expectedOutput);
                    log.info("Program Output: \n" + output);
                    return new EvaluateResult(
                        EvaluateStatus.WA,
                        """
                            答案错误：
                                预期输出：
                                %s
                                实际输出：
                                %s
                            """.formatted(expectedOutput, output),
                        time / 1000.0,
                        memory
                    );
                }
            } else if (!finished) {
                log.info("Time Limit Exceeded.");
                run.destroy();
                return new EvaluateResult(EvaluateStatus.TLE, "Time Limit Exceeded.", 2.0, null);
            } else if (run.exitValue() != 0) {
                log.info("Runtime Error.");
                String errors = new String(run.getErrorStream()
                    .readAllBytes());
                log.info("Program Output: \n" + errors);
                run.destroy();
                return new EvaluateResult(EvaluateStatus.RE, errors, null, null);
            }
            return new EvaluateResult(EvaluateStatus.RE, "评测失败", null, null);
        } finally {
            // 删除跑完之后的文件
            if (new File(javaFileName).exists()) {
                log.info("Delete .java :" + new File(javaFileName).delete());
                log.info("Delete Runner.java :" + new File(runnerFileName).delete());
            }
            if (new File(javaFileName.replace(".java", "") + ".class").exists()) {
                log.info("Delete .class :" + new File(javaFileName.replace(".java", "") + ".class").delete());
                log.info("Delete Runner.class :" + new File(runnerFileName.replace(".java", "") + ".class").delete());
            }
        }
    }

    @Override
    public EvaluateResult call(){
        return null;
    }

    public static void main(String[] args) {
        String str = "Hello, World!  \n   This is a test\n message.";
        str = str.replaceAll("\\s*\\n\\s*", "\n");
        System.out.println(str);
    }

}

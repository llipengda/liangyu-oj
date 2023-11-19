package com.zybzyb.liangyuoj.util;
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

/**
 * @author xw
 * @version 2023/11/19
 * zyb可以看看这个，也可以直接把这个完善一下
 * 输入的代码应该是由别的地方传过来，然后要把运行结果返回给别的地方
 * 测试用例的输入也应该由别的地方传入
 * 也就是说，别的地方调用这里的一个方法，传入代码和测试用例的输入，然后这里需要返回运行的结果
 * 还没做的：还没对测试用例输入进行处理，也没统计运行时间，还有异常处理
 * 还要考虑输入的代码类名的问题，这里写死了
 */
public class Evaluate {

    public static void execute(String sourceCode,String expectedOutput){
        String workDir = ".\\tests\\";
        if(!new File(workDir).exists()){
            if(new File(workDir).mkdirs()){
                System.out.println("create work directory success");
            }else {
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
                return;
            }

            // 编译如果失败了，就打印一个编译失败+原因
            if (compile.exitValue() != 0) {
                String errors = new String(compile.getErrorStream().readAllBytes());
                System.out.println("Compilation Error: \n" + errors);
                return;
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
                } else {
                    System.out.println("Wrong Answer.");
                }
            } else if(!finished){
                System.out.println("Time Limit Exceeded.");
            }else {
                System.out.println("Runtime Error.");
                System.out.println("Program Output: \n" + new String(run.getErrorStream().readAllBytes()));
            }
        } catch (IOException | InterruptedException e) {
            // 这里先全都printStackTrace了
            e.printStackTrace();
        }finally {
            // 删除跑完之后的文件
            if(new File(javaFileName).exists()){
                System.out.println("delete .java :" + new File(javaFileName).delete());
            }
            if(new File(javaFileName.replace(".java","") + ".class").exists()){
                System.out.println("delete .class :" + new File(javaFileName.replace(".java","") + ".class").delete());
            }
        }
    }

    public static void main(String[] args) {
        String sourceCode = """
                public class Solution {
                    public static void main(String[] args) {
                        System.out.println("Hello World!");
                    }
                }
            """;
        execute(sourceCode,"Hello World!");
    }
}
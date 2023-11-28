package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.annotation.NoAuth;
import com.zybzyb.liangyuoj.common.Page;
import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.controller.request.AddProblemRequest;
import com.zybzyb.liangyuoj.controller.request.TryProblemRequest;
import com.zybzyb.liangyuoj.controller.request.UpdateProblemRequest;
import com.zybzyb.liangyuoj.entity.*;
import com.zybzyb.liangyuoj.service.ProblemService;
import com.zybzyb.liangyuoj.util.JWTUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pro")
@Validated
@Tag(name = "题目接口", description = "题目相关接口")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    /**
     * 添加题目
     * 
     * @param addProblemRequest 题目信息
     * @return 添加结果 1成功 0失败
     * @throws Exception 异常
     */
    @PostMapping("/add")
    public Result<Problem> addProblem(@RequestBody AddProblemRequest addProblemRequest) throws Exception {
        return Result.success(problemService.add(addProblemRequest));
    }

    /**
     * 获取简要题目列表
     * 
     * @param chapter 章节
     * @return 简要题目列表
     */
    @GetMapping("/getBriefList")
    public Result<List<BriefProblem>> getBriefList(Integer chapter) {
        return Result.success(problemService.getBriefList(chapter));
    }

    /**
     * 获取题目信息
     * 
     * @param id 题目 ID
     * @return 题目信息
     */
    @GetMapping("/getDetail")
    public Result<Problem> getDetail(Long id) {
        return Result.success(problemService.getDetail(id));
    }

    /**
     * 更新题目信息
     * 
     * @param updateProblemRequest 题目信息
     * @return 更新结果
     * @throws Exception 异常
     */
    @PutMapping("/update")
    public Result<Problem> updateProblem(@RequestBody UpdateProblemRequest updateProblemRequest) throws Exception {
        return Result.success(problemService.update(updateProblemRequest));
    }

    /**
     * 删除题目
     * 
     * @param id 题目 ID
     * @return null
     */
    @DeleteMapping("/delete")
    public Result<Boolean> deleteProblem(Long id) {
        return Result.success(problemService.delete(id));
    }

    /**
     * 尝试题目
     * 
     * @param tryProblemRequest 尝试信息
     * @param request           请求
     * @return 尝试结果
     * @throws Exception 异常
     */
    @PostMapping("/try")
    public Result<EvaluateResult> tryProblem(@RequestBody TryProblemRequest tryProblemRequest,
                                             HttpServletRequest request) throws Exception {
        Long userId = JWTUtil.getUserIdFromRequest(request);
        return Result.success(problemService.evaluate(tryProblemRequest, userId));
    }

    /**
     * 获取提交列表
     * 
     * @param problemId 题目 ID
     * @param page      页码
     * @param pageSize  页大小
     * @return 提交列表
     */
    @GetMapping("/getSubmissionList")
    public Result<Page<Submission>> getSubmissionList(Long problemId, Integer page, Integer pageSize) {
        return Result.success(problemService.getSubmissionList(problemId, page, pageSize));
    }

    /**
     * 获取章节列表
     * @return 章节列表
     */
    @NoAuth
    @GetMapping("/getChapterList")
    public Result<List<Map<String,Object>>> getChapterList() {
        return Result.success(problemService.getChapterList());
    }

    @GetMapping("/getSubmissionById")
    public Result<Submission> getSubmissionById(Long id) {
        return Result.success(problemService.getSubmissionById(id));
    }

}
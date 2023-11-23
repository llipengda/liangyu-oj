package com.zybzyb.liangyuoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zybzyb.liangyuoj.common.CommonErrorCode;
import com.zybzyb.liangyuoj.common.Page;
import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import com.zybzyb.liangyuoj.common.exception.CommonException;
import com.zybzyb.liangyuoj.controller.request.AddProblemRequest;
import com.zybzyb.liangyuoj.controller.request.TryProblemRequest;
import com.zybzyb.liangyuoj.controller.request.UpdateProblemRequest;
import com.zybzyb.liangyuoj.entity.*;
import com.zybzyb.liangyuoj.mapper.ProblemMapper;
import com.zybzyb.liangyuoj.mapper.SubmissionMapper;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import com.zybzyb.liangyuoj.util.EvaluateUtil;
import com.zybzyb.liangyuoj.util.ReflectUtil;
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
    private ProblemMapper problemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubmissionMapper submissionMapper;

    /**
     * 添加题目
     * @param addProblemRequest 题目信息
     * @return 添加结果 1成功 0失败
     */
    @PostMapping("/add")
    public Result<Integer> addProblem(@RequestBody AddProblemRequest addProblemRequest){
        try{
            Problem problem = Problem.builder().createTime(new Date()).submitted(0).accepted(0).build();
            ReflectUtil.add(problem, addProblemRequest);
            return Result.success(problemMapper.insert(problem));
        }catch (CommonException e){
            return Result.fail(e.getCommonErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取简要题目列表
     * @param chapter 章节
     * @return 简要题目列表
     */
    @GetMapping("/getBriefList")
    public Result<List<BriefProblem>> getBriefList(Integer chapter){
        try{
            List<BriefProblem> briefProblemList = problemMapper.getBriefProblemList(chapter);
            briefProblemList.sort(Comparator.comparing(BriefProblem::getCreateTime));
            return Result.success(briefProblemList);
        }catch (CommonException e){
            return Result.fail(e.getCommonErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取题目信息
     * @param id 题目 ID
     * @return 题目信息
     */
    @GetMapping("/getDetail")
    public Result<Problem> getDetail(Long id){
        try{
            return Result.success(problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", id)));
        }catch (CommonException e){
            return Result.fail(e.getCommonErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 更新题目信息
     * @param updateProblemRequest 题目信息
     * @return 更新结果 1成功 0失败
     */
    @PutMapping("/update")
    public Result<Integer> updateProblem(@RequestBody UpdateProblemRequest updateProblemRequest){
        try{
            Problem problem = problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", updateProblemRequest.getId()));
            ReflectUtil.update(problem, updateProblemRequest);
            return Result.success(problemMapper.updateById(problem));
        }catch (CommonException e){
            return Result.fail(e.getCommonErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除题目
     * @param id 题目 ID
     * @return 删除结果 1成功 0失败
     */
    @DeleteMapping("/delete")
    public Result<Integer> deleteProblem(Long id){
        try{
            Problem p = problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", id));
            p.setDeleteTime(new Date());
            return Result.success(problemMapper.updateById(p));
        }catch (CommonException e){
            return Result.fail(e.getCommonErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 尝试题目
     * @param tryProblemRequest 尝试信息
     * @param request 请求
     * @return 尝试结果
     */
    @PostMapping("/try")
    public Result<EvaluateResult> tryProblem(@RequestBody TryProblemRequest tryProblemRequest, HttpServletRequest request){
        try{
            Problem p = problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", tryProblemRequest.getProblemId()));
            System.out.println(1);
            JWTUser jwtUser = (JWTUser) request.getSession().getAttribute("user");
            System.out.println(2);
            User user = userMapper.selectById(jwtUser.getId());
            System.out.println(3);

            Submission submission = Submission.builder()
                    .problemId(p.getId())
                    .userId(user.getId())
                    .code(tryProblemRequest.getCode())
                    .submitTime(new Date())
                    .build();

            EvaluateResult res = EvaluateUtil.execute(tryProblemRequest.getCode(),p.getSampleOutput());

            submission.setResult(res.getStatus().toString());
            if(res.getTime()!=null){
                submission.setTime(res.getTime());
            }
            submissionMapper.insert(submission);

            p.setSubmitted(p.getSubmitted() + 1);
            user.setSubmitted(user.getSubmitted() + 1);

            if(res.getStatus() == EvaluateStatus.AC){
                p.setAccepted(p.getAccepted() + 1);
                user.setSolved(user.getSolved() + 1);
            }

            problemMapper.updateById(p);
            return Result.success(res);
        }catch (CommonException e){
            return Result.fail(e.getCommonErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 获取提交列表
     * @param problemId 题目 ID
     * @param page 页码
     * @param pageSize 页大小
     * @return 提交列表
     */
    @GetMapping("/getSubmissionList")
    public Result<List<Submission>> getSubmissionList(Long problemId,Integer page,Integer pageSize){
        try{
            PageHelper.startPage(page, pageSize, "submit_time desc");
            QueryWrapper<Submission> wrapper = new QueryWrapper<>();
            wrapper.eq("problem_id", problemId);
            return Result.success(new Page<>(new PageInfo<>(submissionMapper.selectList(wrapper))).getList());
        }catch (CommonException e){
            return Result.fail(e.getCommonErrorCode());
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }







}
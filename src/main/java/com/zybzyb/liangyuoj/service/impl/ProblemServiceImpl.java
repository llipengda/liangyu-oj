package com.zybzyb.liangyuoj.service.impl;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.zybzyb.liangyuoj.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zybzyb.liangyuoj.common.Page;
import com.zybzyb.liangyuoj.common.enumeration.EvaluateStatus;
import com.zybzyb.liangyuoj.controller.request.AddProblemRequest;
import com.zybzyb.liangyuoj.controller.request.TryProblemRequest;
import com.zybzyb.liangyuoj.controller.request.UpdateProblemRequest;
import com.zybzyb.liangyuoj.mapper.ProblemMapper;
import com.zybzyb.liangyuoj.mapper.SubmissionMapper;
import com.zybzyb.liangyuoj.mapper.UserMapper;
import com.zybzyb.liangyuoj.service.ProblemService;
import com.zybzyb.liangyuoj.util.EvaluateUtil;
import com.zybzyb.liangyuoj.util.ReflectUtil;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Override
    public Problem add(AddProblemRequest addProblemRequest) throws Exception {
        Problem problem = Problem.builder()
            .createTime(new Date())
            .submitted(0)
            .accepted(0)
            .build();
        problem.setSampleInput(addProblemRequest.getAllInput().get(0));
        problem.setSampleOutput(addProblemRequest.getAllOutput().get(0));
        problem.setAllInput(JSONObject.toJSONString(addProblemRequest.getAllInput()));
        problem.setAllOutput(JSONObject.toJSONString(addProblemRequest.getAllOutput()));
        ReflectUtil.add(problem, addProblemRequest);
        problemMapper.insert(problem);
        return problem;
    }

    @Override
    public List<BriefProblem> getBriefList(Integer chapter) {
        List<BriefProblem> briefProblemList = problemMapper.getBriefProblemList(chapter);
        briefProblemList.sort(Comparator.comparing(BriefProblem::getCreateTime));
        return briefProblemList;
    }

    @Override
    public ProblemDto getDetail(Long id) {
        return new ProblemDto(problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", id)));
    }

    @Override
    public Problem update(UpdateProblemRequest updateProblemRequest) throws Exception {
        Problem problem = problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", updateProblemRequest
            .getId()));
        if(updateProblemRequest.getAllInput().containsAll(JSON.parseObject(problem.getAllInput(),new TypeReference<List<String>>(){}))){
            problem.setAllInput(JSONObject.toJSONString(updateProblemRequest.getAllInput()));
        }
        if(updateProblemRequest.getAllOutput().containsAll(JSON.parseObject(problem.getAllOutput(),new TypeReference<List<String>>(){}))){
            problem.setAllOutput(JSONObject.toJSONString(updateProblemRequest.getAllOutput()));
        }
        ReflectUtil.update(problem, updateProblemRequest);
        problemMapper.updateById(problem);
        return problem;
    }

    @Override
    public boolean delete(Long id) {
        Problem p = problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", id));
        p.setDeleteTime(new Date());
        return problemMapper.updateById(p) == 1;
    }

    @Override
    public EvaluateResult evaluate(TryProblemRequest tryProblemRequest, Long userId) throws Exception {
        Problem p = problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", tryProblemRequest.getProblemId()));
        User user = userMapper.selectById(userId);

        Submission submission = Submission.builder()
            .problemId(p.getId())
            .userId(user.getId())
            .userName(user.getNickname())
            .code(tryProblemRequest.getCode())
            .submitTime(new Date())
            .problemName(p.getTitle())
            .build();

        ExecutorService service = Executors.newFixedThreadPool(10);
        List<String> inputs = JSON.parseObject(p.getAllInput(), new TypeReference<>() {});
        List<String> outputs = JSON.parseObject(p.getAllOutput(), new TypeReference<>() {});
        List<Callable<EvaluateResult>> tasks = new ArrayList<>();
        EvaluateResult res = new EvaluateResult();
        for(int x = 0 ; x < inputs.size() && x < outputs.size() ; ++x){
            int finalX = x;
            tasks.add(() -> EvaluateUtil.execute(tryProblemRequest.getCode(),inputs.get(finalX),outputs.get(finalX)));
        }
        try{
            List<Future<EvaluateResult>> futures = service.invokeAll(tasks);
            for(Future<EvaluateResult> future : futures){
                EvaluateResult result = future.get();
                if(result.getStatus() != EvaluateStatus.AC){
                    res.setStatus(result.getStatus());
                    res.setMessage(result.getMessage());
                }
                if(result.getTime() != null){
                    res.setTime(res.getTime() + result.getTime());
                }
                if(result.getMemory() != null){
                    res.setMemory(res.getMemory() + result.getMemory());
                }
            }
        }finally {
            service.shutdown();
        }

        submission.setResult(res.getStatus().toString());
        if(res.getTime() != null && res.getTime() != 0){
            submission.setTime(res.getTime() / inputs.size());
            res.setTime(submission.getTime());
        }
        if(res.getMemory() != null && res.getMemory() != 0){
            submission.setMemory(res.getMemory() / inputs.size());
            res.setMemory(submission.getMemory());
        }
        submissionMapper.insert(submission);

        p.setSubmitted(p.getSubmitted() + 1);
        user.setSubmitted(user.getSubmitted() + 1);

        if (res.getStatus() == EvaluateStatus.AC) {
            p.setAccepted(p.getAccepted() + 1);
            user.setSolved(user.getSolved() + 1);
        }

        problemMapper.updateById(p);
        userMapper.updateById(user);

        return res;
    }

    @Override
    public Page<Submission> getSubmissionList(Long problemId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "submit_time desc");
        QueryWrapper<Submission> wrapper = new QueryWrapper<>();
        wrapper.eq("problem_id", problemId);
        return new Page<>(new PageInfo<>(submissionMapper.selectList(wrapper)));
    }

    @Override
    public List<Map<String,Object>> getChapterList() {
        QueryWrapper<Problem> wrapper = new QueryWrapper<>();
        wrapper.select("DISTINCT chapter","chapter_name");
        wrapper.orderBy(true,true,"chapter");
        problemMapper.selectMaps(wrapper);
        return problemMapper.selectMaps(wrapper);
    }

    @Override
    public Submission getSubmissionById(Long id) {
        return submissionMapper.selectById(id);
    }
}

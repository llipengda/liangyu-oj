package com.zybzyb.liangyuoj.service.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
import com.zybzyb.liangyuoj.entity.BriefProblem;
import com.zybzyb.liangyuoj.entity.EvaluateResult;
import com.zybzyb.liangyuoj.entity.Problem;
import com.zybzyb.liangyuoj.entity.Submission;
import com.zybzyb.liangyuoj.entity.User;
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
    public Problem getDetail(Long id) {
        return problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", id));
    }

    @Override
    public Problem update(UpdateProblemRequest updateProblemRequest) throws Exception {
        Problem problem = problemMapper.selectOne(new QueryWrapper<Problem>().eq("id", updateProblemRequest
            .getId()));
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
            .code(tryProblemRequest.getCode())
            .submitTime(new Date())
            .build();

        EvaluateResult res = EvaluateUtil.execute(tryProblemRequest.getCode(), p.getSampleOutput());

        submission.setResult(res.getStatus()
            .toString());
        if (res.getTime() != null) {
            submission.setTime(res.getTime());
        }
        submissionMapper.insert(submission);

        p.setSubmitted(p.getSubmitted() + 1);
        user.setSubmitted(user.getSubmitted() + 1);

        if (res.getStatus() == EvaluateStatus.AC) {
            p.setAccepted(p.getAccepted() + 1);
            user.setSolved(user.getSolved() + 1);
        }

        problemMapper.updateById(p);

        return res;
    }

    @Override
    public Page<Submission> getSubmissionList(Long problemId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "submit_time desc");
        QueryWrapper<Submission> wrapper = new QueryWrapper<>();
        wrapper.eq("problem_id", problemId);
        return new Page<>(new PageInfo<>(submissionMapper.selectList(wrapper)));
    }
}

package com.zybzyb.liangyuoj.service;

import java.util.List;
import java.util.Map;

import com.zybzyb.liangyuoj.common.Page;
import com.zybzyb.liangyuoj.controller.request.AddProblemRequest;
import com.zybzyb.liangyuoj.controller.request.TryProblemRequest;
import com.zybzyb.liangyuoj.controller.request.UpdateProblemRequest;
import com.zybzyb.liangyuoj.entity.*;


public interface ProblemService {

    Problem add(AddProblemRequest addProblemRequest) throws Exception;

    List<BriefProblem> getBriefList(Integer chapter);

    ProblemDto getDetail(Long id);

    Problem update(UpdateProblemRequest updateProblemRequest) throws Exception;

    boolean delete(Long id);

    EvaluateResult evaluate(TryProblemRequest tryProblemRequest, Long userId) throws Exception;

    Page<Submission> getSubmissionList(Long problemId, Integer page, Integer pageSize);

    List<Map<String,Object>> getChapterList();

    Submission getSubmissionById(Long id);

}
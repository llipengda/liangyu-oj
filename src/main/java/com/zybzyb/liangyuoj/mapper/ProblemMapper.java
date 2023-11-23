package com.zybzyb.liangyuoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zybzyb.liangyuoj.entity.BriefProblem;
import com.zybzyb.liangyuoj.entity.Problem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemMapper extends BaseMapper<Problem> {

    @Select("SELECT id, title, create_time, accepted, submitted, difficulty FROM problem WHERE chapter = #{chapter} AND delete_time IS NULL")
    List<BriefProblem> getBriefProblemList(@Param("chapter")Integer chapter);

}

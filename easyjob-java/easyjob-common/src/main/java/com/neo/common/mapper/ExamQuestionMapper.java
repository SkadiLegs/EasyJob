package com.neo.common.mapper;

import com.neo.common.entity.po.ExamQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamQuestionMapper extends MyBaseMapper<ExamQuestion> {
    void updateBatchStatus(@Param("List") List<String> questionIds, @Param("query") ExamQuestion examQuestion);


}

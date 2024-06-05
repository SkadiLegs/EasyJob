package com.neo.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neo.common.entity.po.QuestionInfo;
import com.neo.common.entity.query.QuestionInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface QuestionInfoMapper extends BaseMapper<QuestionInfo> {
    Integer updateByParam(@Param("bean") QuestionInfo bean, @Param("query") QuestionInfoQuery query);

    void updateBatchByQIFId(@Param("questionIds") List<String> queryParams, @Param("query") QuestionInfo questionInfo);

    void insertBatch(@Param("queryList") ArrayList<QuestionInfo> questionList);
}

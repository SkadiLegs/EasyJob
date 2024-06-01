package com.neo.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.QuestionInfo;
import com.neo.common.entity.query.QuestionInfoQuery;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName examQuestionService
 * @MethodName
 * @Params
 */
public interface QuestionInfoService extends IService<QuestionInfo> {
    Page<QuestionInfo> selectPageQIList(QuestionInfoQuery query);

    void saveOrUpdateQIF(Boolean isSuperAdmin, QuestionInfo questionInfo);

    QuestionInfo showDetailNext(QuestionInfoQuery query, Integer nextType, Integer currentId, boolean b);

    void updateByParam(QuestionInfo questionInfo, QuestionInfoQuery params);


    void removeBatchQIF(String questionIds, Integer userId);
}

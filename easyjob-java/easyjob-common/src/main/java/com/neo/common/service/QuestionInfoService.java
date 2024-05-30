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
}

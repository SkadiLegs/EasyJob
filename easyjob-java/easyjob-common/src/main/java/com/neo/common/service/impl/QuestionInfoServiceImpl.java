package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.po.QuestionInfo;
import com.neo.common.entity.query.QuestionInfoQuery;
import com.neo.common.mapper.QuestionInfoMapper;
import com.neo.common.service.QuestionInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName QuestionInfoServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class QuestionInfoServiceImpl extends ServiceImpl<QuestionInfoMapper, QuestionInfo> implements QuestionInfoService {
    @Resource
    QuestionInfoService questionInfoService;

    @Override
    public Page<QuestionInfo> selectPageQIList(QuestionInfoQuery query) {
        Page<QuestionInfo> questionInfoPage = new Page<>(query.getPageNo() == null ? 1 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        QueryWrapper<QuestionInfo> queryWrapper = new QueryWrapper();
        if (query.getTitleFuzzy() != null) {
            queryWrapper.like("title", query.getTitleFuzzy());
        }
        if (query.getCreateUserNameFuzzy() != null) {
            queryWrapper.like("create_user_name", query.getCreateUserNameFuzzy());
        }
        if (query.getDifficultyLevel() != null) {
            queryWrapper.eq("difficulty_level", query.getDifficultyLevel());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq("status", query.getStatus());
        }
        if (query.getCategoryId() != null) {
            queryWrapper.eq("category_id", query.getCategoryId());
        }
        queryWrapper.orderByDesc(query.getOrderBy());
        Page<QuestionInfo> page = questionInfoService.page(questionInfoPage, queryWrapper);
        return page;
    }
}

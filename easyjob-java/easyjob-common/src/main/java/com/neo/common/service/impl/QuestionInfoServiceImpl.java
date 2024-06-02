package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.config.AppConfig;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.enums.PostStatusEnum;
import com.neo.common.entity.po.Category;
import com.neo.common.entity.po.QuestionInfo;
import com.neo.common.entity.query.QuestionInfoQuery;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.ACommonMapper;
import com.neo.common.mapper.QuestionInfoMapper;
import com.neo.common.service.CategoryService;
import com.neo.common.service.QuestionInfoService;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    QuestionInfoMapper questionInfoMapper;
    @Resource
    QuestionInfoService questionInfoService;
    @Resource
    CategoryService categoryService;
    @Resource
    AppConfig appConfig;

    @Resource
    ACommonMapper aCommonMapper;

    @Override
    public Page<QuestionInfo> selectPageQIList(QuestionInfoQuery query) {
        Page<QuestionInfo> questionInfoPage = new Page<>(query.getPageNo() == null ? 1 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        QueryWrapper<QuestionInfo> queryWrapper = judgeTextQW(query);
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
        return questionInfoMapper.selectPage(questionInfoPage, queryWrapper);
    }

    @Override
    public void saveOrUpdateQIF(Boolean isSuperAdmin, QuestionInfo questionInfo) {
        Category CategoryById = categoryService.getById(questionInfo.getCategoryId());
        questionInfo.setCategoryName(CategoryById.getCategoryName());
        if (questionInfo.getQuestionId() == null) {
            questionInfoMapper.insert(questionInfo);
        } else {
            QuestionInfo dbQIF = questionInfoMapper.selectById(questionInfo);
            if (!dbQIF.getCreateUserId().equals(questionInfo.getCreateUserId()) && !isSuperAdmin) {
                throw new EasyJobException(ResultCode.ERROR_NOPERMISSION, "修改者全权限不足");
            }
            questionInfo.setCreateTime(null);
            questionInfo.setCreateUserName(null);
            questionInfo.setCategoryId(null);
            questionInfoMapper.updateById(questionInfo);
        }
    }

    /**
     * @Description TODO
     * @Author Lenove
     * @Date 2024/6/1
     * @MethodName showDetailNext
     * @Param query:问题详情 ,nextType: 上一条问题的id , currentId: , updateReadCount:
     * @Return: null
     */
    @Override
    public QuestionInfo showDetailNext(QuestionInfoQuery query, Integer nextType, Integer currentId, boolean updateReadCount) {
        if (nextType == null) {
            query.setQuestionId(currentId);
        } else {
            query.setNextType(nextType);
            query.setCurrentId(currentId);
        }

        // 获取问题详情
        QueryWrapper<QuestionInfo> queryWrapper = new QueryWrapper();
        queryWrapper.last("limit 0,1");
        QuestionInfo questionInfo = questionInfoMapper.selectOne(queryWrapper);
        if (questionInfo == null && nextType == null) {
            throw new EasyJobException(ResultCode.NOT_FOUND, "内容不存在");
        } else if (questionInfo == null && nextType == -1) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "已经是第一条了");
        } else if (questionInfo == null && nextType == 1) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "已经是最后一条了");
        }
        if (updateReadCount && questionInfo != null) {
            aCommonMapper.updateCount(Constants.TABLE_NAME_QUESTION_INFO, 1, null, currentId);
            questionInfo.setReadCount((questionInfo.getReadCount() == null ? 0 : questionInfo.getReadCount()) + 1);
        }
        return questionInfo;
    }


    /**
     * @Description 当query.getQueryTextContent为false排除question和answer_analysis
     * @Author Lenove
     * @Date 2024/6/1
     * @MethodName judgeTextQW
     * @Param query 查询条件
     * @Return: QueryWrapper<QuestionInfo>
     */
    protected QueryWrapper<QuestionInfo> judgeTextQW(QuestionInfoQuery query) {
        QueryWrapper<QuestionInfo> queryWrapper = new QueryWrapper<>();
        if (query.getQueryTextContent() != null && !query.getQueryTextContent()) {
            queryWrapper.select(QuestionInfo.class, info -> !(info.getColumn().equals("question") || info.getColumn().equals("answer_analysis")));
        }
        return queryWrapper;
    }

    @Override
    public void updateByParam(QuestionInfo questionInfo, QuestionInfoQuery params) {
    }

    @Override
    public void removeBatchQIF(String questionIds, Integer userId) {
        String[] questionIdsArray = questionIds.split(",");
        if (userId != null && !userId.equals(appConfig.getSuperAdminPhones())) {
            QuestionInfoQuery queryQIF = new QuestionInfoQuery();
            queryQIF.setQuestionIds(questionIdsArray);
            QueryWrapper<QuestionInfo> queryWrapper = judgeTextQW(queryQIF);
            List<QuestionInfo> questionInfoList = questionInfoMapper.selectList(queryWrapper);
            // 过滤出不属于当前用户的数据列表
            List<QuestionInfo> currentUserDataList = questionInfoList.stream()
                    .filter(a -> !a.getCreateUserId().equals(String.valueOf(userId)))
                    .collect(Collectors.toList());
            if (!currentUserDataList.isEmpty()) {
                throw new EasyJobException(ResultCode.ERROR_NAN, "当前用户可删除的数据列表为空");
            }
        }
        QueryWrapper<QuestionInfo> queryDel = new QueryWrapper<>();
        queryDel.eq("status", PostStatusEnum.NO_POST.getStatus());
        if (userId != null) {
            queryDel.eq("create_user_id", userId);
        }
        //转化为int类型数组
        List<String> list = Arrays.asList(questionIdsArray);
        queryDel.in("question_id", list);
        questionInfoMapper.delete(queryDel);
    }


    @Override
    public void updateBatchByQIFId(QuestionInfo questionInfo, QuestionInfoQuery queryParams) {
        List<String> list = Arrays.asList(queryParams.getQuestionIds());
        questionInfoMapper.updateBatchByQIFId(list, questionInfo);
    }
}

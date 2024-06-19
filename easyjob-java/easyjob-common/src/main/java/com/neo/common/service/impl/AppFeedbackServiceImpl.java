package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.FeedbackSendTypeEnum;
import com.neo.common.entity.enums.FeedbackStatusEnum;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.po.AppFeedback;
import com.neo.common.entity.query.AppFeedbackQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.mapper.AppFeedbackMapper;
import com.neo.common.service.AppFeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName AppFeedbackServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class AppFeedbackServiceImpl extends ServiceImpl<AppFeedbackMapper, AppFeedback> implements AppFeedbackService {

    @Resource
    AppFeedbackMapper appFeedbackMapper;

    @Override
    public PaginationResultVO<AppFeedback> findListByPage(AppFeedbackQuery query) {
        Page<AppFeedback> page = new Page<>(query.getPageNo() == null ? 0 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        QueryWrapper<AppFeedback> queryWrapper = new QueryWrapper<>();
        if (query.getStatus() != null) {
            queryWrapper.eq("status", query.getStatus());
        }
        if (query.getCreateTimeStart() != null && query.getCreateTimeEnd() != null) {
            queryWrapper.between("create_time", query.getCreateTimeStart(), query.getCreateTimeEnd());
        }
        if (query.getNickNameFuzzy() != null) {
            queryWrapper.like("nick_name", query.getNickNameFuzzy());
        }
        queryWrapper.orderByDesc(query.getOrderByAsc());

        Page<AppFeedback> appFeedbackPage = appFeedbackMapper.selectPage(page, queryWrapper);
        //筛选掉已经回复的问题
//        List<AppFeedback> collect = appFeedbackPage.getRecords().stream().filter(item -> !item.getStatus().equals(PostStatusEnum.POST.getStatus())).collect(Collectors.toList());
        PaginationResultVO<AppFeedback> paginationResultVO = new PaginationResultVO<>(
                (int) appFeedbackPage.getTotal(),
                (int) appFeedbackPage.getSize(),
                (int) appFeedbackPage.getCurrent(),
                (int) appFeedbackPage.getPages(),
                appFeedbackPage.getRecords());
        return paginationResultVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replyFeedbackById(AppFeedbackQuery query) {
        // 查询要回复的反馈问题状态
        AppFeedback dbAppFeedback = appFeedbackMapper.selectById(query.getFeedbackId());
        // 回复的sql设置其父id为要回复的反馈的feedbackId
        Integer pFeedbackId = dbAppFeedback.getFeedbackId();
        // 父级id为0时说明是第一次回复
        if (dbAppFeedback.getPFeedbackId() != 0) {
            pFeedbackId = dbAppFeedback.getPFeedbackId();
        }

        AppFeedback appFeedback = new AppFeedback();
        appFeedback.setPFeedbackId(pFeedbackId);
        appFeedback.setSendType(FeedbackSendTypeEnum.ADMIN.getStatus());
        appFeedback.setNickName(query.getNickName());
        appFeedback.setStatus(FeedbackStatusEnum.REPLY.getStatus());
        appFeedback.setContent(query.getContent());
        appFeedback.setUserId(query.getUserId());
        appFeedbackMapper.insert(appFeedback);

        /**
         * 更新父级状态
         */
        AppFeedback replyPFeedback = new AppFeedback();
        replyPFeedback.setFeedbackId(query.getFeedbackId());
        replyPFeedback.setStatus(FeedbackStatusEnum.REPLY.getStatus());
        appFeedbackMapper.updateById(replyPFeedback);
    }

    /**
     * @Description 查询选中问题的 提问/回复 记录
     * @Author Lenove
     * @Date 2024/6/17
     * @MethodName getReplyFeedback
     * @Param :
     * AppFeedbackQuery appFeedbackQuery:包含排序方式和选中的问题id
     * @Return: null
     */
    @Override
    public List<AppFeedback> getReplyFeedback(AppFeedbackQuery appFeedbackQuery) {
        AppFeedback DBappFeedback = appFeedbackMapper.selectById(appFeedbackQuery.getFeedbackId());
        List<AppFeedback> appFeedbacks = new ArrayList<>();
        QueryWrapper<AppFeedback> queryWrapper = new QueryWrapper();
        /**
         * 当选中问题的pFeedbackId=0时代表选中的是一个新问题,查询出和他feedbackId相同的pFeedbackId的数据和他本身并加入返回的List
         *当选中问题的pFeedbackId!=0时代表选中的是一个追问,这时直接查询这个问题的父问题,再查询和他pFeedbackId相同的其他数据,然后返回
         */
        if (DBappFeedback.getPFeedbackId() != 0) {
            queryWrapper.eq("p_feedback_id", DBappFeedback.getPFeedbackId()).or().eq("feedback_id", DBappFeedback.getPFeedbackId()).orderByAsc(appFeedbackQuery.getOrderByAsc());
            appFeedbacks = appFeedbackMapper.selectList(queryWrapper);
        } else {
            queryWrapper.eq("p_feedback_id", appFeedbackQuery.getFeedbackId()).or().eq("feedback_id", appFeedbackQuery.getFeedbackId()).orderByAsc(appFeedbackQuery.getOrderByAsc());
            List<AppFeedback> appFeedbacks_db = appFeedbackMapper.selectList(queryWrapper);
            appFeedbacks.addAll(appFeedbacks_db);
        }
        return appFeedbacks;
    }
}

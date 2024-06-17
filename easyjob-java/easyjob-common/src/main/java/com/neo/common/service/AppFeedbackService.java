package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.AppFeedback;
import com.neo.common.entity.query.AppFeedbackQuery;
import com.neo.common.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName AppFeedbackService
 * @MethodName
 * @Params
 */
public interface AppFeedbackService extends IService<AppFeedback> {
    PaginationResultVO<AppFeedback> findListByPage(AppFeedbackQuery query);

    //回复反馈
    void replyFeedbackById(AppFeedbackQuery appFeedbackQuery);

    //反馈恢复列表
    List<AppFeedback> getReplyFeedback(AppFeedbackQuery appFeedbackQuery);
}

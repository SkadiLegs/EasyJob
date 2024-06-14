package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.entity.po.AppFeedback;
import com.neo.common.entity.query.AppFeedbackQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.service.AppFeedbackService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName AppFeedbackController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/appFeedback")
public class AppFeedbackController {

    @Autowired
    private AppFeedbackService appFeedbackService;

    @PostMapping("loadFeedback")
    @GlobalInterceptor
    public R loadFeedback(AppFeedbackQuery query) {
        query.setOrderByDesc("feedback_id");
        query.setPFeedbackId(0);
        PaginationResultVO<AppFeedback> appFeedbackList = appFeedbackService.findListByPage(query);
        return R.ok().data(appFeedbackList);
    }
}

package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.po.AppFeedback;
import com.neo.common.entity.query.AppFeedbackQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.service.AppFeedbackService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        query.setOrderByAsc("feedback_id");
        query.setPFeedbackId(0);
        PaginationResultVO<AppFeedback> appFeedbackList = appFeedbackService.findListByPage(query);
        return R.ok().data(appFeedbackList);
    }

    @PostMapping("loadFeedbackReply")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_FEEDBACK_REPLY)
    public R replyFeedback(@VerifyParam(required = true) @RequestParam(value = "pFeedbackId") Integer feedbackId) {
        AppFeedbackQuery appFeedbackQuery = new AppFeedbackQuery();
        appFeedbackQuery.setFeedbackId(feedbackId);
        appFeedbackQuery.setOrderByAsc("feedback_id");
        List<AppFeedback> list = appFeedbackService.getReplyFeedback(appFeedbackQuery);
        return R.ok().data(list);
    }

    @PostMapping("replyFeedback")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_FEEDBACK_REPLY)
    public R replyFeedback(HttpSession session,
                           @VerifyParam(required = true, max = 500) String content,
                           @VerifyParam(required = true) @RequestParam(value = "pFeedbackId") Integer feedbackId) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);

        AppFeedbackQuery query = new AppFeedbackQuery();
        query.setNickName(sessionUserAdminDto.getUserName());
        query.setFeedbackId(feedbackId);
        query.setContent(content);
        query.setUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        appFeedbackService.replyFeedbackById(query);
        return R.ok();
    }


}

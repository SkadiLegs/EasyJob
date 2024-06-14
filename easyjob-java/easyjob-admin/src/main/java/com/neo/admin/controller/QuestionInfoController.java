package com.neo.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.dto.ImportErrorItem;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.enums.PostStatusEnum;
import com.neo.common.entity.po.QuestionInfo;
import com.neo.common.entity.query.QuestionInfoQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.service.QuestionInfoService;
import com.neo.common.uilts.CommonUtils;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/30
 * @ClassName QuestionInfoController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/questionInfo")
public class QuestionInfoController {

    @Autowired
    QuestionInfoService questionInfoService;

    @PostMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_LIST)
    public R LoadDataList(QuestionInfoQuery query) {
        query.setOrderByDesc("question_id");
        query.setQueryTextContent(true);
        Page<QuestionInfo> questionInfos = questionInfoService.selectPageQIList(query);
        PaginationResultVO paginationResultVO = new PaginationResultVO<>(
                (int) questionInfos.getTotal(),
                (int) questionInfos.getSize(),
                (int) questionInfos.getCurrent(),
                (int) questionInfos.getPages(),
                questionInfos.getRecords());
        return R.ok().data(paginationResultVO);
    }

    @PostMapping("/saveQuestionInfo")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_EDIT)
    public R saveQuestionInfo(HttpSession session, QuestionInfo questionInfo) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        questionInfo.setCreateUserName(sessionUserAdminDto.getUserName());
        questionInfo.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        questionInfoService.saveOrUpdateQIF(sessionUserAdminDto.getSuperAdmin(), questionInfo);
        return R.ok();
    }

    @PostMapping("/showQuestionDetailNext")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_LIST)
    public R showQuestionDetailNext(QuestionInfoQuery query, Integer nextType,
                                    @VerifyParam(required = true) Integer currentId) {
        QuestionInfo questionInfo = questionInfoService.showDetailNext(query, nextType, currentId, false);
        return R.ok().data(questionInfo);
    }


    @RequestMapping("/delQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_DEL)
    public R delQuestion(HttpSession session, @VerifyParam(required = true) Integer questionId) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        questionInfoService.removeBatchQIF(String.valueOf(questionId), sessionUserAdminDto.getSuperAdmin() ? null : sessionUserAdminDto.getUserId());
        return R.ok();
    }

    @RequestMapping("/delQuestionBatch")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_DEL_BATCH)
    public R delQuestionBatch(@VerifyParam(required = true) String questionIds) {
        questionInfoService.removeBatchQIF(questionIds, null);
        return R.ok();
    }

    /**
     * @Description 发布问题
     * @Author Lenove
     * @Date 2024/6/3
     * @MethodName cancelPostQuestion
     * @Param questionIds:需要发布的questionId
     * @Return: null
     */
    @RequestMapping("/postQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_POST)
    public R postQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.POST.getStatus());
        return R.ok();
    }

    /**
     * @Description 取消发布问题
     * @Author Lenove
     * @Date 2024/6/3
     * @MethodName cancelPostQuestion
     * @Param questionIds:需要取消发布的questionId
     * @Return: null
     */
    @PostMapping("/cancelPostQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_POST)
    public R cancelPostQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.NO_POST.getStatus());
        return R.ok();
    }

    private void updateStatus(String questionIds, Integer status) {
        QuestionInfoQuery queryParams = new QuestionInfoQuery();
        queryParams.setQuestionIds(questionIds.split(","));
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setStatus(status);
        CommonUtils.checkParam(queryParams);
        questionInfoService.updateBatchByQIFId(questionInfo, queryParams);
    }

    @RequestMapping("/importQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.QUESTION_IMPORT)
    public R importQuestion(HttpSession session, MultipartFile file) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        List<ImportErrorItem> list = questionInfoService.importQuestion(file, sessionUserAdminDto);
        return R.ok().data(list);
    }

}

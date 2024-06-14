package com.neo.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.dto.ImportErrorItem;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.enums.PostStatusEnum;
import com.neo.common.entity.enums.QuestionTypeEnum;
import com.neo.common.entity.po.ExamQuestion;
import com.neo.common.entity.po.ExamQuestionItem;
import com.neo.common.entity.query.ExamQuestionQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.service.ExamQuestionItemService;
import com.neo.common.service.ExamQuestionService;
import com.neo.common.uilts.CommonUtils;
import com.neo.common.uilts.JsonUtils;
import com.neo.common.uilts.R;
import com.neo.common.uilts.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/6
 * @ClassName ExamQuestionController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/examQuestion")
public class ExamQuestionController {

    @Autowired
    private ExamQuestionService examQuestionService;

    @Autowired
    private ExamQuestionItemService examQuestionItemService;

    @PostMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_LIST)
    public R loadDataList(ExamQuestionQuery query) {
        query.setOrderByAsc("question_id");
        // 是否查询答案
        query.setQueryAnswer(true);
        PaginationResultVO<ExamQuestion> listByPage = examQuestionService.findListByPage(query);
        return R.ok().data(listByPage);
    }

    @PostMapping("/saveExamQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_EDIT)
    public R saveExamQuestion(HttpSession session,
                              @VerifyParam(required = true) ExamQuestion examQuestion,
                              String questionItemListJson) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        examQuestion.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        examQuestion.setCreateUserName(sessionUserAdminDto.getUserName());
        List<ExamQuestionItem> examQuestionItemList = new ArrayList<>();
        if (!QuestionTypeEnum.TRUE_FALSE.getType().equals(examQuestion.getQuestionType())) {
            if (CommonUtils.isEmpty(questionItemListJson)) {
                throw new EasyJobException(ResultCode.ERROR_600, "参数类型错误");
            }
            examQuestionItemList = JsonUtils.convertJsonArray2List(questionItemListJson, ExamQuestionItem.class);
        }
        examQuestionService.saveExamQuestion(examQuestionItemList, examQuestion, sessionUserAdminDto.getSuperAdmin());
        return R.ok();
    }

    @RequestMapping("/loadQuestionItem")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_EDIT)
    public R loadQuestionItem(@VerifyParam(required = true) Integer questionId) {
        QueryWrapper<ExamQuestionItem> queryWrapper = new QueryWrapper();
        queryWrapper.eq("question_id", questionId);
        List<ExamQuestionItem> listByQuestionId = examQuestionItemService.list(queryWrapper);
        return R.ok().data(listByQuestionId);
    }


    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL)
    @PostMapping("/postExamQuestion")
    public R postExamQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.POST.getStatus());
        return R.ok();
    }

    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL)
    @PostMapping("/cancelPostExamQuestion")
    public R cancelPostExamQuestion(@VerifyParam(required = true) String questionIds) {
        updateStatus(questionIds, PostStatusEnum.NO_POST.getStatus());
        return R.ok();
    }

    private void updateStatus(String questionIds, Integer status) {
        ExamQuestionQuery query = new ExamQuestionQuery();
        query.setQuestionIds(questionIds.split(","));
        ExamQuestion examQuestion = new ExamQuestion();
        examQuestion.setStatus(status);
        examQuestionService.updateBatch(query, examQuestion);
    }

    @PostMapping("/delExamQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL)
    public R delExamQuestion(HttpSession session, @VerifyParam(required = true) Integer questionId) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        List<String> questionIds = new ArrayList<>();
        questionIds.add(String.valueOf(questionId));
        examQuestionService.removeExamQuestion(questionIds, sessionUserAdminDto.getSuperAdmin() ? null : sessionUserAdminDto.getUserId());
        return R.ok();
    }

    @PostMapping("/delExamQuestionBatch")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_DEL_BATCH)
    public R delExamQuestionBatch(HttpSession session, @VerifyParam(required = true) String questionIds) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        List<String> questionIdList = Arrays.asList(questionIds.split(","));
        examQuestionService.removeExamQuestion(questionIdList, sessionUserAdminDto.getSuperAdmin() ? null : sessionUserAdminDto.getUserId());
        return R.ok();
    }

    @RequestMapping("/importExamQuestion")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.EXAM_QUESTION_IMPORT)
    public R importExamQuestion(HttpSession session, MultipartFile file) {
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        List<ImportErrorItem> importErrorItems = examQuestionService.importExamQuestion(file, sessionUserAdminDto);
        return R.ok().data(importErrorItems);
    }


}

package com.neo.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.po.QuestionInfo;
import com.neo.common.entity.query.QuestionInfoQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.service.QuestionInfoService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        query.setOrderBy("question_id");
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
}

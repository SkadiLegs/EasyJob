package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.dto.ImportErrorItem;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.po.ExamQuestion;
import com.neo.common.entity.po.ExamQuestionItem;
import com.neo.common.entity.query.ExamQuestionQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName examQuestionService
 * @MethodName
 * @Params
 */
public interface ExamQuestionService extends IService<ExamQuestion> {
    PaginationResultVO<ExamQuestion> findListByPage(ExamQuestionQuery query);

    void saveExamQuestion(List<ExamQuestionItem> examQuestionItemList, ExamQuestion examQuestion, boolean isSuperAdmin);

    void updateBatch(ExamQuestionQuery query, ExamQuestion examQuestion);

    void removeExamQuestion(List<String> questionIds, Integer integer);

    List<ImportErrorItem> importExamQuestion(MultipartFile file, SessionUserAdminDto sessionUserAdminDto);
}

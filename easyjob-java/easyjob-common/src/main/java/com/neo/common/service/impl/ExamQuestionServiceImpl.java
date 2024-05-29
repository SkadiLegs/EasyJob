package com.neo.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.po.ExamQuestion;
import com.neo.common.mapper.ExamQuestionMapper;
import com.neo.common.service.ExamQuestionService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName examQuestionServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class ExamQuestionServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion> implements ExamQuestionService {
}

package com.neo.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.po.QuestionInfo;
import com.neo.common.mapper.QuestionInfoMapper;
import com.neo.common.service.QuestionInfoService;
import org.springframework.stereotype.Service;

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
}

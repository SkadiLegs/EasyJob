package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.po.Category;
import com.neo.common.entity.po.ExamQuestion;
import com.neo.common.entity.po.ExamQuestionItem;
import com.neo.common.entity.query.ExamQuestionQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.CategoryMapper;
import com.neo.common.mapper.ExamQuestionItemMapper;
import com.neo.common.mapper.ExamQuestionMapper;
import com.neo.common.service.ExamQuestionService;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Resource
    ExamQuestionMapper examQuestionMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    ExamQuestionItemMapper examQuestionItemMapper;

    @Override
    public PaginationResultVO<ExamQuestion> findListByPage(ExamQuestionQuery query) {
        Page<ExamQuestion> page = new Page<>(query.getPageNo() == null ? 1 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        QueryWrapper<ExamQuestion> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(query.getOrderBy());
        //queryAnswer==false是排除字段"questionAnswer"
        if (!query.getQueryAnswer()) {
            queryWrapper.select(ExamQuestion.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("question_answer") && !tableFieldInfo.getColumn().equals("answer_analysis"));
        }
        if (query.getTitleFuzzy() != null) {
            queryWrapper.like("title", query.getTitleFuzzy());
        }
        if (query.getCreateUserNameFuzzy() != null) {
            queryWrapper.like("create_user_name", query.getCreateUserNameFuzzy());
        }
        if (query.getDifficultyLevel() != null) {
            queryWrapper.eq("difficulty_level", query.getDifficultyLevel());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq("status", query.getStatus());
        }
        if (query.getCategoryId() != null) {
            queryWrapper.eq("category_id", query.getCategoryId());
        }
        if (query.getQuestionType() != null) {
            queryWrapper.eq("question_type", query.getQuestionType());
        }
        Page<ExamQuestion> examQuestionPage = examQuestionMapper.selectPage(page, queryWrapper);
        PaginationResultVO<ExamQuestion> paginationResultVO = new PaginationResultVO<>(
                (int) examQuestionPage.getTotal(),
                (int) examQuestionPage.getSize(),
                (int) examQuestionPage.getCurrent(),
                (int) examQuestionPage.getPages(),
                examQuestionPage.getRecords());
        return paginationResultVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveExamQuestion(List<ExamQuestionItem> examQuestionItemList, ExamQuestion examQuestion, boolean isSuperAdmin) {
        Category category = categoryMapper.selectById(examQuestion.getCategoryId());
        if (category == null) {
            throw new EasyJobException(ResultCode.ERROR_600, "数据类型错误");
        }
        if (examQuestion.getQuestionId() == null) {
            examQuestion.setCategoryName(category.getCategoryName());
            examQuestionMapper.insert(examQuestion);
        } else {
            ExamQuestion dbInfo = this.examQuestionMapper.selectById(examQuestion.getQuestionId());
            if (!dbInfo.getCreateUserId().equals(examQuestion.getCreateUserId()) && !isSuperAdmin) {
                throw new EasyJobException(ResultCode.ERROR_600, "网络异常");
            }
            examQuestion.setQuestionType(null);
            examQuestion.setCreateUserId(null);
            examQuestion.setCreateUserName(null);
            examQuestionMapper.updateById(examQuestion);
        }
        examQuestionItemList.forEach(item -> {
            item.setQuestionId(examQuestion.getQuestionId());
        });
        /**
         * item.getItemId()!=null说明是需要更新的选项
         */
        List<ExamQuestionItem> updateItemList = examQuestionItemList.stream().filter(item -> item.getItemId() != null).collect(Collectors.toList());
        /**
         * item.getItemId()==null说明是需要新增的选项
         */
        List<ExamQuestionItem> addItemList = examQuestionItemList.stream().filter(item -> item.getItemId() == null).collect(Collectors.toList());

        // 将要更新的选项列表转换为 Map，以选项 ID 为键，选项对象为值
        Map<Integer, ExamQuestionItem> paramItemMap = updateItemList.stream().collect(Collectors.toMap(ExamQuestionItem::getItemId, Function.identity(), (data1, data2) -> data2));
        // 查询数据库中已存在的问题选项列表
        List<ExamQuestionItem> dbExamQuestionItems = examQuestionItemMapper.selectList(new QueryWrapper<ExamQuestionItem>().eq("question_id", examQuestion.getQuestionId()));
        //构建删除队列
        ArrayList<Integer> delList = new ArrayList<>();
        //如果paramItemMap中没有db中的数据,则说明id=db.getItemId()是需要被删除的选项
        if (!paramItemMap.isEmpty()) {
            for (ExamQuestionItem db : dbExamQuestionItems) {
                if (paramItemMap.get(db.getItemId()) == null) {
                    delList.add(db.getItemId());
                }
            }
        }
        //进行更新修改删除操作
        if (!addItemList.isEmpty()) {
            examQuestionItemMapper.insertBatchSomeColumn(addItemList);
        }

        for (ExamQuestionItem item : updateItemList) {
            examQuestionItemMapper.updateById(item);
        }

        if (!delList.isEmpty()) {
            examQuestionItemMapper.deleteBatchIds(delList);
        }
    }
}

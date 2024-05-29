package com.neo.common.entity.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 考试题目参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamQuestionQuery extends BaseParam {


    /**
     * 问题ID
     */
    private Integer questionId;

    /**
     * 标题
     */
    private String title;

    private String titleFuzzy;

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    private String categoryNameFuzzy;

    /**
     * 难度
     */
    private Integer difficultyLevel;

    /**
     * 问题类型 0:判断 1:单选题 2:多选
     */
    private Integer questionType;

    /**
     * 问题描述
     */
    private String question;

    private String questionFuzzy;

    /**
     * 答案
     */
    private String questionAnswer;

    private String questionAnswerFuzzy;

    /**
     * 回答解释
     */
    private String answerAnalysis;

    private String answerAnalysisFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 0:未发布 1:已发布
     */
    private Integer status;

    /**
     * 用户ID
     */
    private String createUserId;

    private String createUserIdFuzzy;

    /**
     * 姓名
     */
    private String createUserName;

    private String createUserNameFuzzy;

    /**
     * 0:内部 1:外部投稿
     */
    private Integer postUserType;

    private Boolean queryAnswer;

    private String[] questionIds;

    private Integer nextType;

    private Integer currentId;

    private Boolean queryQuestionItem;

    private String[] categoryIds;

    private List<Integer> excludeQuestionIdList;


}

package com.neo.common.entity.query;


import java.util.List;

/**
 * 考试问题参数
 */
public class AppExamQuestionQuery extends BaseParam {


    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 考试ID
     */
    private Integer examId;

    /**
     * 用户ID
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 问题ID
     */
    private Integer questionId;

    /**
     * 用户答案
     */
    private String userAnswer;

    private String userAnswerFuzzy;

    /**
     * 0:未作答 1:正确  2:错误
     */
    private Integer answerResult;

    private Boolean showUserAnswer;

    private List<String> questionIds;

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    public Boolean getShowUserAnswer() {
        return showUserAnswer;
    }

    public void setShowUserAnswer(Boolean showUserAnswer) {
        this.showUserAnswer = showUserAnswer;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Integer getExamId() {
        return this.examId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserIdFuzzy(String userIdFuzzy) {
        this.userIdFuzzy = userIdFuzzy;
    }

    public String getUserIdFuzzy() {
        return this.userIdFuzzy;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionId() {
        return this.questionId;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getUserAnswer() {
        return this.userAnswer;
    }

    public void setUserAnswerFuzzy(String userAnswerFuzzy) {
        this.userAnswerFuzzy = userAnswerFuzzy;
    }

    public String getUserAnswerFuzzy() {
        return this.userAnswerFuzzy;
    }

    public void setAnswerResult(Integer answerResult) {
        this.answerResult = answerResult;
    }

    public Integer getAnswerResult() {
        return this.answerResult;
    }

}

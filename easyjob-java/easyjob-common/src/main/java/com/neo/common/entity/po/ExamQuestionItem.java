package com.neo.common.entity.po;

import java.io.Serializable;


/**
 *
 */
public class ExamQuestionItem implements Serializable {


    private static final long serialVersionUID = -2630327750617171871L;
    /**
     * 选项ID
     */
    private Integer itemId;

    /**
     * 问题ID
     */
    private Integer questionId;

    /**
     * 标题
     */
    private String title;

    /**
     * 排序
     */
    private Integer sort;


    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemId() {
        return this.itemId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getQuestionId() {
        return this.questionId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

    @Override
    public String toString() {
        return "选项ID:" + (itemId == null ? "空" : itemId) + "，问题ID:" + (questionId == null ? "空" : questionId) + "，标题:" + (title == null ? "空" : title) + "，排序:" + (sort == null ? "空" : sort);
    }
}

package com.neo.common.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neo.common.annotation.VerifyParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 考试题目
 */
@Data
@AllArgsConstructor
public class ExamQuestion implements Serializable {


    private static final long serialVersionUID = -7660107708676523920L;
    /**
     * 问题ID
     */
    @TableId(type = IdType.AUTO)
    private Integer questionId;

    /**
     * 标题
     */
    @VerifyParam(required = true)
    private String title;

    /**
     * 分类ID
     */
    @VerifyParam(required = true)
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 难度
     */
    @VerifyParam(required = true)
    private Integer difficultyLevel;

    /**
     * 问题类型 0:判断 1:单选题 2:多选
     */
    @VerifyParam(required = true)
    private Integer questionType;

    /**
     * 问题描述
     */
    private String question;

    /**
     * 答案
     */
    @VerifyParam(required = true)
    private String questionAnswer;

    /**
     * 回答解释
     */
    @VerifyParam(required = true)
    private String answerAnalysis;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 0:未发布 1:已发布
     */
    private Integer status;

    /**
     * 用户ID
     */
    private String createUserId;

    /**
     * 姓名
     */
    private String createUserName;

    /**
     * 0:内部 1:外部投稿
     */
    private Integer postUserType;

    @TableField(exist = false)
    List<ExamQuestionItem> questionItemList;


}

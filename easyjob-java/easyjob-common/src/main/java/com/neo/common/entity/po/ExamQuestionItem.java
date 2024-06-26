package com.neo.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionItem implements Serializable {


    private static final long serialVersionUID = -2630327750617171871L;
    /**
     * 选项ID
     */
    @TableId(type = IdType.AUTO)
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

}

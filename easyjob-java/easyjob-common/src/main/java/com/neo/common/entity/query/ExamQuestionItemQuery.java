package com.neo.common.entity.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamQuestionItemQuery extends BaseParam {


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

    private String titleFuzzy;

    /**
     * 排序
     */
    private Integer sort;


    private List<String> questionIdList;


}

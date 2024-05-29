package com.neo.common.entity.query;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryQuery extends BaseParam {


    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 名称
     */
    private String categoryName;

    private String categoryNameFuzzy;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String iconPath;

    private String iconPathFuzzy;

    /**
     * 背景颜色
     */
    private String bgColor;

    private String bgColorFuzzy;

    /**
     * 0:问题分类 1:考题分类 2:问题分类和考题分类
     */
    private Integer type;

    private Integer[] types;


}

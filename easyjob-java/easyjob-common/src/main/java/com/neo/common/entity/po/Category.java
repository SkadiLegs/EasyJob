package com.neo.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.neo.common.annotation.VerifyParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 分类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {


    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 名称
     */
    @VerifyParam(required = true)
    private String categoryName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String iconPath;

    /**
     * 背景颜色
     */
    private String bgColor;

    /**
     * 0:问题分类 1:考题分类 2:问题分类和考题分类
     */
    @VerifyParam(required = true)
    private Integer type;


}

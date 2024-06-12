package com.neo.common.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;


/**
 * app轮播
 */
@Data
public class AppCarousel implements Serializable {


    /**
     * 自增ID
     */
    @TableId
    private Integer carouselId;

    /**
     * 图片
     */
    private String imgPath;

    /**
     * 0:分享1:问题 2:考题 3:外部连接
     */
    private Integer objectType;

    /**
     * 文章ID
     */
    private String objectId;

    /**
     * 外部连接
     */
    private String outerLink;

    /**
     * 排序
     */
    private Integer sort;


    @Override
    public String toString() {
        return "自增ID:" + (carouselId == null ? "空" : carouselId) + "，图片:" + (imgPath == null ? "空" : imgPath) + "，0:分享1:问题 2:考题 3:外部连接:" + (objectType == null ? "空" : objectType) + "，文章ID:" + (objectId == null ? "空" : objectId) + "，外部连接:" + (outerLink == null ? "空" : outerLink) + "，排序:" + (sort == null ? "空" : sort);
    }
}

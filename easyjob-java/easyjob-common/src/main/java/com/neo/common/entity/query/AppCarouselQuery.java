package com.neo.common.entity.query;


/**
 * app轮播参数
 */
public class AppCarouselQuery extends BaseParam {


    /**
     * 自增ID
     */
    private Integer carouselId;

    /**
     * 图片
     */
    private String imgPath;

    private String imgPathFuzzy;

    /**
     * 0:分享1:问题 2:考题 3:外部连接
     */
    private Integer objectType;

    /**
     * 文章ID
     */
    private String objectId;

    private String objectIdFuzzy;

    /**
     * 外部连接
     */
    private String outerLink;

    private String outerLinkFuzzy;

    /**
     * 排序
     */
    private Integer sort;


    public void setCarouselId(Integer carouselId) {
        this.carouselId = carouselId;
    }

    public Integer getCarouselId() {
        return this.carouselId;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPathFuzzy(String imgPathFuzzy) {
        this.imgPathFuzzy = imgPathFuzzy;
    }

    public String getImgPathFuzzy() {
        return this.imgPathFuzzy;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectType() {
        return this.objectType;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectIdFuzzy(String objectIdFuzzy) {
        this.objectIdFuzzy = objectIdFuzzy;
    }

    public String getObjectIdFuzzy() {
        return this.objectIdFuzzy;
    }

    public void setOuterLink(String outerLink) {
        this.outerLink = outerLink;
    }

    public String getOuterLink() {
        return this.outerLink;
    }

    public void setOuterLinkFuzzy(String outerLinkFuzzy) {
        this.outerLinkFuzzy = outerLinkFuzzy;
    }

    public String getOuterLinkFuzzy() {
        return this.outerLinkFuzzy;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

}

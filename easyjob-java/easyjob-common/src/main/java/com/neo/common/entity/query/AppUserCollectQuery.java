package com.neo.common.entity.query;

import java.util.List;


/**
 * 用户收藏参数
 */
public class AppUserCollectQuery extends BaseParam {


    /**
     * 收藏ID
     */
    private Integer collectId;

    /**
     * 用户ID
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 主体ID 问题ID,考题ID,分享文章ID
     */
    private String objectId;

    private String objectIdFuzzy;

    /**
     * 0:分享收藏 1:问题收藏  2:考题收藏
     */
    private Integer collectType;

    /**
     * 收藏时间
     */
    private String collectTime;

    private String collectTimeStart;

    private String collectTimeEnd;

    private List<String> objectIdList;

    private Integer nextType;

    private Integer currentId;

    public Integer getCurrentId() {
        return currentId;
    }

    public void setCurrentId(Integer currentId) {
        this.currentId = currentId;
    }

    public Integer getNextType() {
        return nextType;
    }

    public void setNextType(Integer nextType) {
        this.nextType = nextType;
    }

    public List<String> getObjectIdList() {
        return objectIdList;
    }

    public void setObjectIdList(List<String> objectIdList) {
        this.objectIdList = objectIdList;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }

    public Integer getCollectId() {
        return this.collectId;
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

    public void setCollectType(Integer collectType) {
        this.collectType = collectType;
    }

    public Integer getCollectType() {
        return this.collectType;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getCollectTime() {
        return this.collectTime;
    }

    public void setCollectTimeStart(String collectTimeStart) {
        this.collectTimeStart = collectTimeStart;
    }

    public String getCollectTimeStart() {
        return this.collectTimeStart;
    }

    public void setCollectTimeEnd(String collectTimeEnd) {
        this.collectTimeEnd = collectTimeEnd;
    }

    public String getCollectTimeEnd() {
        return this.collectTimeEnd;
    }

}

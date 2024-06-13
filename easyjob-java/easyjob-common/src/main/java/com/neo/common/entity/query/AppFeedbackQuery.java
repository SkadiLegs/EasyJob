package com.neo.common.entity.query;


/**
 * 问题反馈参数
 */
public class AppFeedbackQuery extends BaseParam {


    /**
     * 自增ID
     */
    private Integer feedbackId;

    /**
     * 用户ID
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 昵称
     */
    private String nickName;

    private String nickNameFuzzy;

    /**
     * 反馈内容
     */
    private String content;

    private String contentFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 父级ID
     */
    private Integer pFeedbackId;

    /**
     * 状态0:未回复 1:已回复
     */
    private Integer status;

    /**
     * 0:访客 1:管理员
     */
    private Integer sendType;

    /**
     * 访客最后发送时间
     */
    private String clientLastSendTime;

    private String clientLastSendTimeStart;

    private String clientLastSendTimeEnd;


    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getFeedbackId() {
        return this.feedbackId;
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

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickNameFuzzy(String nickNameFuzzy) {
        this.nickNameFuzzy = nickNameFuzzy;
    }

    public String getNickNameFuzzy() {
        return this.nickNameFuzzy;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContentFuzzy(String contentFuzzy) {
        this.contentFuzzy = contentFuzzy;
    }

    public String getContentFuzzy() {
        return this.contentFuzzy;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeStart() {
        return this.createTimeStart;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeEnd() {
        return this.createTimeEnd;
    }

    public void setpFeedbackId(Integer pFeedbackId) {
        this.pFeedbackId = pFeedbackId;
    }

    public Integer getpFeedbackId() {
        return this.pFeedbackId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getSendType() {
        return this.sendType;
    }

    public void setClientLastSendTime(String clientLastSendTime) {
        this.clientLastSendTime = clientLastSendTime;
    }

    public String getClientLastSendTime() {
        return this.clientLastSendTime;
    }

    public void setClientLastSendTimeStart(String clientLastSendTimeStart) {
        this.clientLastSendTimeStart = clientLastSendTimeStart;
    }

    public String getClientLastSendTimeStart() {
        return this.clientLastSendTimeStart;
    }

    public void setClientLastSendTimeEnd(String clientLastSendTimeEnd) {
        this.clientLastSendTimeEnd = clientLastSendTimeEnd;
    }

    public String getClientLastSendTimeEnd() {
        return this.clientLastSendTimeEnd;
    }

}

package com.neo.common.entity.query;


import lombok.Data;

/**
 * 问题反馈参数
 */
@Data
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

}

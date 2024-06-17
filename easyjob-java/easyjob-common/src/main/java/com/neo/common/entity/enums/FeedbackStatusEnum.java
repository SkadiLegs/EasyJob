package com.neo.common.entity.enums;

public enum FeedbackStatusEnum {
    NO_REPLY(0, "未回复"), REPLY(1, "已回复");

    private Integer status;
    private String description;

    FeedbackStatusEnum(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static FeedbackStatusEnum getByStatus(Integer status) {
        for (FeedbackStatusEnum at : FeedbackStatusEnum.values()) {
            if (at.status.equals(status)) {
                return at;
            }
        }
        return null;
    }
}

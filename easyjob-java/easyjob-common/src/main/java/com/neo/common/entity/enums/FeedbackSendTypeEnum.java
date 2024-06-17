package com.neo.common.entity.enums;

public enum FeedbackSendTypeEnum {
    CLIENT(0, "访客"), ADMIN(1, "管理员");

    private Integer status;
    private String description;

    FeedbackSendTypeEnum(int status, String description) {
        this.status = status;
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static FeedbackSendTypeEnum getByStatus(Integer status) {
        for (FeedbackSendTypeEnum at : FeedbackSendTypeEnum.values()) {
            if (at.status.equals(status)) {
                return at;
            }
        }
        return null;
    }
}

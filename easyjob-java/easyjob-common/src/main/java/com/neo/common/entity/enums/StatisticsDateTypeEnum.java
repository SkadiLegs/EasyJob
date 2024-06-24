package com.neo.common.entity.enums;

public enum StatisticsDateTypeEnum {
    APP_DOWNLOAD(0, "App下载"), REGISTER_USER(1, "注册用户"), QUESTION_INFO(3, "八股文"),
    EXAM(4, "考题"), SHARE(5, "分享"), FEEDBACK(6, "反馈");

    private Integer type;
    private String description;

    StatisticsDateTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static StatisticsDateTypeEnum getByType(Integer type) {
        for (StatisticsDateTypeEnum item : StatisticsDateTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }
        return null;
    }
}

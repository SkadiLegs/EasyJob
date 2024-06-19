package com.neo.common.entity.enums;

public enum AppUpdateTypeEnum {
    ALL(0, ".apk", "全更新"), WGT(1, ".wgt", "局部热更新");

    private Integer type;
    private String suffix;
    private String description;

    AppUpdateTypeEnum(int type, String suffix, String description) {
        this.type = type;
        this.suffix = suffix;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getDescription() {
        return description;
    }

    public static AppUpdateTypeEnum getByType(Integer type) {
        for (AppUpdateTypeEnum at : AppUpdateTypeEnum.values()) {
            if (at.getType().equals(type)) {
                return at;
            }
        }
        return null;
    }
}

package com.neo.common.entity.enums;


public enum QuestionTypeEnum {

    TRUE_FALSE(0, "判断题"),
    RADIO(1, "单选题"),
    CHECK_BOX(2, "多选题");


    private Integer type;
    private String desc;

    QuestionTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static QuestionTypeEnum getByDesc(String desc) {
        for (QuestionTypeEnum item : QuestionTypeEnum.values()) {
            if (item.getDesc().equals(desc)) {
                return item;
            }
        }
        return null;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}

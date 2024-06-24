package com.neo.common.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsDataDto {
    // 要展示的字段
    private String statisticsName;
    // 具体统计数据
    private Integer count;
    // 前一天的具体数据
    private Integer preCount;
    // 用于条形图的时间
    private List<Integer> listData;


}

package com.neo.common.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsDataDto {
    private String statisticsName;
    private Integer count;
    private Integer preCount;
    private List<Integer> listData;


}

package com.neo.common.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatisticsDataWeekDto {
    private List<String> dateList;
    private List<StatisticsDataDto> itemDataList;
}

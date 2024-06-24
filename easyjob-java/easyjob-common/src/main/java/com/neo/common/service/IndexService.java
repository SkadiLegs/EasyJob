package com.neo.common.service;

import com.neo.common.entity.dto.StatisticsDataDto;
import com.neo.common.entity.dto.StatisticsDataWeekDto;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/23
 * @ClassName IndexService
 * @MethodName
 * @Params
 */
public interface IndexService {
    List<StatisticsDataDto> getAllData();

    StatisticsDataWeekDto getAppWeekData();

    StatisticsDataWeekDto getContentWeekData();

}

package com.neo.common.mapper;

import com.neo.common.entity.po.AppCarousel;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName AppCarouselMapper
 * @MethodName
 * @Params
 */
public interface AppCarouselMapper extends MyBaseMapper<AppCarousel> {
    void updateAppCarouselSort(String carouselId, int sort);
}

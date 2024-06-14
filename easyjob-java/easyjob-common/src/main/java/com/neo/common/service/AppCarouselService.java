package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.AppCarousel;
import com.neo.common.entity.query.AppCarouselQuery;
import com.neo.common.entity.vo.PaginationResultVO;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName AppCarouselService
 * @MethodName
 * @Params
 */
public interface AppCarouselService extends IService<AppCarousel> {

    PaginationResultVO findListAppCarouselPage(AppCarouselQuery query);

    void saveAppCarousel(AppCarousel appCarousel);

    void changerAppCarouselSort(String carouselIds);
}

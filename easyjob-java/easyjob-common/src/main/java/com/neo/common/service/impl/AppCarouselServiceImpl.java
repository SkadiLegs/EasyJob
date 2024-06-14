package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.po.AppCarousel;
import com.neo.common.entity.po.AppDevice;
import com.neo.common.entity.query.AppCarouselQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.mapper.AppCarouselMapper;
import com.neo.common.service.AppCarouselService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName AppCarouselServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class AppCarouselServiceImpl extends ServiceImpl<AppCarouselMapper, AppCarousel> implements AppCarouselService {

    @Resource
    AppCarouselMapper appCarouselMapper;

    @Override
    public PaginationResultVO findListAppCarouselPage(AppCarouselQuery query) {
        QueryWrapper<AppCarousel> queryWrapper = new QueryWrapper();
        Page page = new Page(query.getPageNo() == null ? 0 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        queryWrapper.orderByAsc(query.getOrderByAsc());
        Page AppCarouselPage = appCarouselMapper.selectPage(page, queryWrapper);
        PaginationResultVO<AppDevice> paginationResultVO = new PaginationResultVO<>(
                (int) AppCarouselPage.getTotal(),
                (int) AppCarouselPage.getSize(),
                (int) AppCarouselPage.getCurrent(),
                (int) AppCarouselPage.getPages(),
                AppCarouselPage.getRecords());
        return paginationResultVO;
    }

    @Override
    public void saveAppCarousel(AppCarousel appCarousel) {

        if (appCarousel.getCarouselId() == null) {
            Integer max_sort = appCarouselMapper.selectOne(new QueryWrapper<AppCarousel>().select("MAX(sort) as sort")).getSort();
            appCarousel.setSort(max_sort + 1);
            appCarouselMapper.insert(appCarousel);
        } else {
            appCarouselMapper.updateById(appCarousel);
        }
    }

    @Override
    public void changerAppCarouselSort(String carouselIds) {
        List<String> carouselIds_list = Arrays.asList(carouselIds.split(","));
        QueryWrapper<AppCarousel> queryWrapper = new QueryWrapper();
        queryWrapper.in("carousel_id", carouselIds_list);
        int sort_num = 1;
        for (String carouselId : carouselIds_list) {
            appCarouselMapper.updateAppCarouselSort(carouselId, sort_num++);
        }

    }
}

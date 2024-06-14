package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.po.AppCarousel;
import com.neo.common.entity.query.AppCarouselQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.service.AppCarouselService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName AppCarouselController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/appCarousel")
public class AppCarouselController {

    @Autowired
    private AppCarouselService appCarouselService;

    @RequestMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_CAROUSEL_LIST)
    public R loadDataList(AppCarouselQuery query) {
        query.setOrderByAsc("sort");
        PaginationResultVO paginationResultVO = appCarouselService.findListAppCarouselPage(query);
        return R.ok().data(paginationResultVO.getList());
    }


    @RequestMapping("/saveCarousel")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_CAROUSEL_EDIT)
    public R saveCarousel(AppCarousel appCarousel) {
        appCarouselService.saveAppCarousel(appCarousel);
        return R.ok();
    }

    @RequestMapping("/changeSort")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_CAROUSEL_EDIT)
    public R changerSort(@VerifyParam(required = true) String carouselIds) {
        appCarouselService.changerAppCarouselSort(carouselIds);
        return R.ok();
    }


}

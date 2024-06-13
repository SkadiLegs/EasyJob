package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.enums.UserStatusEnum;
import com.neo.common.entity.po.AppDevice;
import com.neo.common.entity.po.AppUserInfo;
import com.neo.common.entity.query.AppDeviceQuery;
import com.neo.common.entity.query.AppUserInfoQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.service.AppDeviceService;
import com.neo.common.service.AppUserInfoService;
import com.neo.common.uilts.R;
import com.neo.common.uilts.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/13
 * @ClassName AppDeviceController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/appUser")
public class AppUserInfoController {

    @Autowired
    AppDeviceService appDeviceService;

    @Autowired
    private AppUserInfoService appUserInfoService;

    @PostMapping("/loadDeviceList")
    @GlobalInterceptor
    public R loadDeviceList(AppDeviceQuery query) {
        query.setOrderBy("create_time");
        PaginationResultVO<AppDevice> listByPage = appDeviceService.findListByPage(query);
        return R.ok().data(listByPage);
    }

    @RequestMapping("/loadDataList")
    @GlobalInterceptor
    public R loadDataList(AppUserInfoQuery query) {
        query.setOrderBy("join_time");
        PaginationResultVO<AppUserInfo> listByPage = appUserInfoService.findListByPage(query);
        return R.ok().data(listByPage);
    }

    @RequestMapping("/updateStatus")
    @GlobalInterceptor
    public R updateStatus(@VerifyParam(required = true) String userId,
                          @VerifyParam(required = true) Integer status) {
        UserStatusEnum userStatusEnum = UserStatusEnum.getByStatus(status);
        if (userStatusEnum == null) {
            throw new EasyJobException(ResultCode.ERROR_600, "状态码错误,请按规范传递参数");
        }
        AppUserInfo appUserInfo = new AppUserInfo();
        appUserInfo.setUserId(userId);
        appUserInfo.setStatus(status);
        appUserInfoService.updateById(appUserInfo);
        return R.ok();
    }
}

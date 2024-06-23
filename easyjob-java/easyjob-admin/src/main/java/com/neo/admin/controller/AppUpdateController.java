package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.po.AppUpdate;
import com.neo.common.entity.query.AppUpdateQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.service.AppUpdateService;
import com.neo.common.uilts.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/19
 * @ClassName AppUpdateController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/app")
public class AppUpdateController {

    @Resource
    private AppUpdateService appUpdateService;

    @PostMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_UPDATE_LIST)
    public R loadDatalist(AppUpdateQuery query) {
        query.setOrderByDesc("id");
        PaginationResultVO<AppUpdate> paginationResultVO = appUpdateService.getAppUpdatePage(query);
        return R.ok().data(paginationResultVO);
    }

    @PostMapping("/saveUpdate")
    @GlobalInterceptor
    public R saveUpdate(Integer id,
                        @VerifyParam(required = true) String version,
                        @VerifyParam(required = true) String updateDesc,
                        @VerifyParam(required = true) Integer updateType,
                        MultipartFile file) {

        AppUpdate appUpdate = new AppUpdate();
        appUpdate.setUpdateDesc(updateDesc);
        appUpdate.setId(id);
        appUpdate.setVersion(version);
        appUpdate.setUpdateType(updateType);
        appUpdateService.saveOrUpdateAppUpdate(appUpdate, file);
        return R.ok();
    }

    @PostMapping("/postUpdate")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_UPDATE_POST)
    public R postUpdate(@VerifyParam(required = true) Integer id,
                        @VerifyParam(required = true) Integer status,
                        String grayscaleDevice) {


        appUpdateService.postAppUpdata(id, status, grayscaleDevice);
        return R.ok();
    }

    @PostMapping("/delUpdate")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.APP_UPDATE_POST)
    public R delUpdateApp(@VerifyParam(required = true) Integer id) {
        appUpdateService.removeAppUpdataByid(id);
        return R.ok();
    }


}

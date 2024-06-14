package com.neo.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.config.AppConfig;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.enums.UserStatusEnum;
import com.neo.common.entity.enums.VerifyRegexEnum;
import com.neo.common.entity.po.SysAccount;
import com.neo.common.entity.query.SysAccountQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.service.SysAccountService;
import com.neo.common.uilts.MD5;
import com.neo.common.uilts.R;
import com.neo.common.uilts.ResultCode;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/26
 * @ClassName SysAccountController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/settings")
public class SysAccountController {
    @Autowired
    SysAccountService sysAccountService;

    @Resource
    private AppConfig appConfig;

    /**
     * @Description TODO
     * @Author Lenove
     * @Date 2024/5/26
     * @MethodName loadAccountList
     * @Param SysAccountQuery 查询SysAccount实体类
     * @Return: null
     */
    @PostMapping("/loadAccountList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_LIST)
    public R loadAccountList(SysAccountQuery sysAccountQuery) {
        sysAccountQuery.setOrderByDesc("create_time");
        Page<SysAccount> sysAccountPage = sysAccountService.selectByPage(sysAccountQuery);
        PaginationResultVO<SysAccount> paginationResultVO =
                new PaginationResultVO<>(
                        (int) sysAccountPage.getTotal(),
                        (int) sysAccountPage.getSize(),
                        (int) sysAccountPage.getCurrent(),
                        (int) sysAccountPage.getPages(),
                        sysAccountPage.getRecords());
        return R.ok().data(paginationResultVO);
    }

    @PostMapping("/updateStatus")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_UPDATE_PASSWORD)
    public R updateStatus(@VerifyParam(required = true) Integer userId, @VerifyParam(required = true) Integer status) {
        UserStatusEnum userStatusEnum = UserStatusEnum.getByStatus(status);
        if (userStatusEnum == null) {
            throw new EasyJobException(ResultCode.ERROR_600, "找不到该类型状态");
        }
        SysAccount updateInfo = new SysAccount();
        updateInfo.setStatus(status);
        updateInfo.setUserId(userId);
        System.out.println("///////////////" + status + "  " + userId);
        sysAccountService.updateById(updateInfo);
        return R.ok();
    }

    @PostMapping("/delAccount")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_DEL)
    public R delAccount(@VerifyParam Integer userId) {
        SysAccount sysAccount = sysAccountService.getSysAccountByUserId(userId);
        if (StringUtils.hasText(appConfig.getSuperAdminPhones()) && ArrayUtils.contains(appConfig.getSuperAdminPhones().split(","), sysAccount.getPhone())) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "系统超级管理员不允许删除");
        }
        sysAccountService.removeById(sysAccount);
        return R.ok();
    }

    @PostMapping("/saveAccount")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_EDIT)
    public R saveAccount(@VerifyParam SysAccount sysAccount) {
        sysAccountService.saveAccountUser(sysAccount);
        return R.ok();
    }

    @PostMapping("/updatePassword")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ACCOUNT_UPDATE_PASSWORD)
    public R updatePassword(@VerifyParam Integer userId,
                            @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD) String password) {
        SysAccount sysAccount = new SysAccount();
        sysAccount.setUserId(userId);
        sysAccount.setPassword(MD5.encrypt(password));
        sysAccountService.updateById(sysAccount);
        return R.ok();
    }

}

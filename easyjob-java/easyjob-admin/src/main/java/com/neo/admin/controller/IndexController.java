package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.uilts.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/23
 * @ClassName IndexController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @Resource
    private IndexService indexService;


    @PostMapping("/getAllData")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.HOME)
    public R getAllData() {

    }
}

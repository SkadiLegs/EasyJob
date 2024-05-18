package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;
import com.neo.common.service.SysMenuService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysMenuController
 * @MethodName
 * @Params
 */
@RestController
public class SysMenuController {

    @Autowired
    SysMenuService sysMenuService;


    /**
     * @Description 根据分页条件查询
     * @Author Lenove
     * @Date 2024/5/15
     * @MethodName loadDataList
     * @Param null
     * @Return: null
     */
    @GetMapping("/settings")
    public R loadDataList() {
        SysMenuQuery query = new SysMenuQuery();
        query.setFormate2Tree(true);
        query.setOrderBy("sort");
        List<SysMenu> menuList = sysMenuService.findLisByParam(query);
        return R.ok().data("menuList", menuList);
    }

    @GetMapping("/saveMenu")
    @GlobalInterceptor
    public R saveMenu(@VerifyParam SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return R.ok();
    }

    @DeleteMapping("/deleteMenu")
    @GlobalInterceptor
    public R deleteMenu(@VerifyParam @RequestBody SysMenu sysMenu) {
        sysMenuService.deleteMenu(sysMenu);
        return R.ok();
    }
}

package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;
import com.neo.common.service.SysMenuService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/settings")
public class SysMenuController extends ABaseController {

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
    @PostMapping("/menuList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_MENU)
    public R loadDataList() {
        SysMenuQuery query = new SysMenuQuery();
        query.setFormate2Tree(true);
        query.setOrderByAsc("sort");
        List<SysMenu> menuList = sysMenuService.findLisByParam(query);
        return R.ok().data(menuList);
    }

    @PostMapping("/saveMenu")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_MENU_EDIT)
    public R saveMenu(@VerifyParam SysMenu sysMenu) {
        sysMenuService.saveMenu(sysMenu);
        return R.ok().data(null);
    }

    @PostMapping("/deleteMenu")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_MENU_EDIT)
    public R deleteMenu(@VerifyParam Integer menuId) {
        sysMenuService.deleteMenu(menuId);
        return R.ok();
    }
}

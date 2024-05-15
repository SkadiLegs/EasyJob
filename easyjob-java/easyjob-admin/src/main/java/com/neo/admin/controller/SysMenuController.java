package com.neo.admin.controller;

import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;
import com.neo.common.service.SysMenuService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
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
    public R loadDataList() {
        SysMenuQuery query = new SysMenuQuery();
        query.setFormate2Tree(true);
        query.setOrderBy("sort asc");
        List<SysMenu> menuList = sysMenuService.findLisByParam(query);
        return R.ok().data("menuList", menuList);
    }
}

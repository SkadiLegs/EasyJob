package com.neo.admin.controller;

import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;
import com.neo.common.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    SysMenuService sysMenuService;

    public static final Integer DEFAULT_ROOT_MENU_ID = 0;
    private static final String ROOT_MENU_NAME = "所有菜单";

    @GetMapping("test")
    public List<SysMenu> TestGETContorller() {
        SysMenuQuery sysMenuQuery = new SysMenuQuery();
        sysMenuQuery.setFormate2Tree(true);
        sysMenuQuery.setOrderBy("sort");
        sysMenuQuery.setOrderBy("sort");
        List<SysMenu> lisByParam = sysMenuService.findLisByParam(sysMenuQuery);
        if (sysMenuQuery.getFormate2Tree() != null && sysMenuQuery.getFormate2Tree()) {
            SysMenu root = new SysMenu();
            root.setMenuId(DEFAULT_ROOT_MENU_ID);
            root.setPId(-1);
            root.setMenuName(ROOT_MENU_NAME);
            lisByParam.add(root);
            lisByParam = convertLine2Tree4Menu(lisByParam, -1);
        }
        return sysMenuService.findLisByParam(sysMenuQuery);
    }

    public List<SysMenu> convertLine2Tree4Menu(List<SysMenu> menuList, Integer pid) {
        List<SysMenu> children = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menu.getMenuId() != null && menu.getPId() != null && menu.getPId().equals(pid)) {
                menu.setChildren(convertLine2Tree4Menu(menuList, menu.getMenuId()));
                children.add(menu);
            }
        }
        return children;
    }
}

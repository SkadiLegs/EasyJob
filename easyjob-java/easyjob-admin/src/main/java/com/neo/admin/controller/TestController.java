package com.neo.admin.controller;

import com.neo.common.entity.po.SysMenu;
import com.neo.common.mapper.SysMenuMapper;
import com.neo.common.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Autowired
    SysMenuService sysMenuService;
    @Resource
    SysMenuMapper sysMenuMapper;

    public static final Integer DEFAULT_ROOT_MENU_ID = 0;
    private static final String ROOT_MENU_NAME = "所有菜单";

    @GetMapping("test")
    public SysMenu TestGETContorller(Integer id) {
        SysMenu menu = sysMenuMapper.selectTest(id);
        return menu;
    }
}

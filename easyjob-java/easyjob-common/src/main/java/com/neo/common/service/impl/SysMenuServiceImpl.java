package com.neo.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;
import com.neo.common.mapper.SysMenuMapper;
import com.neo.common.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysMenuService
 * @MethodName
 * @Params
 */

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    public static final Integer DEFAULT_ROOT_MENU_ID = 0;
    private static final String ROOT_MENU_NAME = "所有菜单";

    @Override
    public List<SysMenu> findLisByParam(SysMenuQuery query) {

        List<SysMenu> menuList = baseMapper.selectList(null);
        if (query.getFormate2Tree() != null && query.getFormate2Tree()) {
            SysMenu root = new SysMenu();
            root.setMenuId(DEFAULT_ROOT_MENU_ID);
            root.setPId(-1);
            root.setMenuName(ROOT_MENU_NAME);
            menuList.add(root);
            menuList = convertLine2Tree4Menu(menuList, -1);
        }

        return null;
    }

    //TODO 菜单递归
    private List<SysMenu> convertLine2Tree4Menu(List<SysMenu> menuList, Integer pid) {

        return null;
    }
}

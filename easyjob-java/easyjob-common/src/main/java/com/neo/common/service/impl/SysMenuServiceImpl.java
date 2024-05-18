package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.SysMenuMapper;
import com.neo.common.service.SysMenuService;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(query.getOrderBy());
        List<SysMenu> menuList = baseMapper.selectList(queryWrapper);
        //加上"所有菜单"的这一属性并递归排序
        if (query.getFormate2Tree() != null && query.getFormate2Tree()) {
            SysMenu root = new SysMenu();
            root.setMenuId(DEFAULT_ROOT_MENU_ID);
            root.setPId(-1);
            root.setMenuName(ROOT_MENU_NAME);
            menuList.add(root);
            menuList = convertLine2Tree4Menu(menuList, -1);
        }

        return menuList;
    }

    /**
     * @Description 菜单递归, 每次在for循环中取出一个SysMenu并递归, 在递归中遍历所有SysMenu,
     * 每次递归中将其list中所有pid=之前取出的SysMenu.menuId的都添加为children属性(List<SysMenu> children
     * @Author Lenove
     * @Date 2024/5/16
     * @MethodName
     * @Param List<SysMenu>,Integer
     * @Return: List<SysMenu>
     */
    @Override
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

    @Override
    public void saveMenu(SysMenu sysMenu) {
        if (sysMenu.getMenuId() == null) {
            baseMapper.insert(sysMenu);
        } else {
            this.baseMapper.updateById(sysMenu);
        }
    }

    @Override
    public void deleteMenu(SysMenu sysMenu) {
        if (sysMenu.getMenuId() != null) {
            baseMapper.deleteById(sysMenu);
        } else {
            throw new EasyJobException(ResultCode.ERROR_NAN, "删除参数异常");
        }
    }
}

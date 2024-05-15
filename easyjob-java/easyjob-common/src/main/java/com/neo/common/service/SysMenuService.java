package com.neo.common.service;

import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysMenuService
 * @MethodName
 * @Params
 */
public interface SysMenuService {
    /**
     * @Description 返回树状结构的菜单
     * @Author Lenove
     * @Date 2024/5/15
     * @MethodName findLisByParam
     * @Param SysMenuQuery
     * @Return: List<SysMenu>
     */
    List<SysMenu> findLisByParam(SysMenuQuery query);
}

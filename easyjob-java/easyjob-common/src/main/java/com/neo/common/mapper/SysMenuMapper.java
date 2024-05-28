package com.neo.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysMenuService
 * @MethodName
 * @Params
 */


public interface SysMenuMapper extends BaseMapper<SysMenu> {

    public List<SysMenu> selectList1(SysMenuQuery sysMenuQuery);

    public List<SysMenu> selectAllMenuByRoleIds(@Param("roleIds") int[] roleIds);

    SysMenu selectTest(@Param("item") Integer item);


}

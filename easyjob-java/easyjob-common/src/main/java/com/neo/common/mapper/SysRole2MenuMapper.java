package com.neo.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neo.common.entity.po.SysRole2Menu;
import org.apache.ibatis.annotations.Param;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysRole2MenuService
 * @MethodName
 * @Params
 */
public interface SysRole2MenuMapper extends BaseMapper<SysRole2Menu> {
    String selectMenuIdsByRoleIds(@Param("roleIds") String[] roleIds);
}

package com.neo.common.service;

import com.neo.common.entity.po.SysRole;
import com.neo.common.entity.query.SysRoleQuery;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysRoleService
 * @MethodName
 * @Params
 */
public interface SysRoleService {
    // 新增和更新权限
    void saveRole(SysRole sysRole);

    //删除
    void deleteRole(SysRole sysRole);

    //查询根据条件分页查询
    List<SysRole> findListByPage(SysRoleQuery query);

    SysRole getSysRoleByRoleId(Integer roleId);
}

package com.neo.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.SysRole;
import com.neo.common.entity.query.SysRoleQuery;
import com.neo.common.entity.vo.PaginationResultVO;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysRoleService
 * @MethodName
 * @Params
 */
public interface SysRoleService extends IService<SysRole> {
    // 新增和更新权限
    void saveRole(SysRole sysRole, String menuId, String halfMenuIds);

    //删除
    Integer deleteRole(Integer RoleId);

    /**
     * 根据条件查询列表
     */

    public Integer findCountByParam(SysRoleQuery param);


    //查询根据条件分页查询
    PaginationResultVO<SysRole> findListByPage(SysRoleQuery query);

    SysRole getSysRoleByRoleId(Integer roleId);

    void saveRoleMenu(Integer RoleId, String menuId, String halfMenuIds);

    /**
     * 根据条件查询列表
     */
    public Page<SysRole> findListByParam(SysRoleQuery param);
}

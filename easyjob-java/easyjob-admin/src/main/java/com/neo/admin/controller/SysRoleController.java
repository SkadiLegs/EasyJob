package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.po.SysRole;
import com.neo.common.entity.query.SysRoleQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.service.SysRoleService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysRoleController
 * @MethodName
 * @Params
 */
@RestController
@RequestMapping("/settings")
public class SysRoleController extends ABaseController {

    @Autowired
    SysRoleService sysRoleService;

    /**
     * 根据条件分页查询
     */
    //TODO 前端不显示数据
    @PostMapping("/loadRoles")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public R loadRoles(SysRoleQuery query) {
        query.setOrderBy("create_time");
        PaginationResultVO<SysRole> listByPage = sysRoleService.findListByPage(query);
        return R.ok().data(listByPage);
    }

    @PostMapping("/loadAllRoles")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public R loadAllRoles() {
        SysRoleQuery query = new SysRoleQuery();
        query.setOrderBy("create_time");
        return R.ok().data(sysRoleService.findListByParam(query).getRecords());
    }

    @PostMapping("/saveRole")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_EDIT)
    public R saveRole(@VerifyParam(required = true) SysRole sysRole,
                      String menuIds,
                      String halfMenuIds) {

        sysRoleService.saveRole(sysRole, menuIds, halfMenuIds);
        return R.ok();
    }

    @PostMapping("/saveRoleMenu")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_EDIT)
    public R saveRoleMenu(@VerifyParam(required = true) Integer roleId,
                          @VerifyParam(required = true) String menuIds,
                          String halfMenuIds) {

        sysRoleService.saveRoleMenu(roleId, menuIds, halfMenuIds);
        return R.ok();
    }

    @PostMapping("/delRole")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_DEL)
    public R deleteRole(@VerifyParam(required = true) Integer roleId) {
        sysRoleService.deleteRole(roleId);
        return R.ok();
    }

    @PostMapping("/getRoleByRoleId")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public R getRoleByRoleId(@VerifyParam(required = true) Integer roleId) {
        SysRole sysRole = sysRoleService.getSysRoleByRoleId(roleId);
        return R.ok().data(sysRole);
    }


}

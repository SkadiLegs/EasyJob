package com.neo.admin.controller;

import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.po.SysRole;
import com.neo.common.entity.query.SysRoleQuery;
import com.neo.common.service.SysRoleService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/loadRoles")
//    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public R loadRoles(SysRoleQuery query) {
        query.setOrderBy("create_time");
        List<SysRole> listByPage = sysRoleService.findListByPage(query);
        return R.ok().data(listByPage);
    }

    @PostMapping("/saveRole")
    public R saveRole(SysRole sysRole) {

        sysRoleService.saveRole(sysRole);
        return R.ok();
    }

    @DeleteMapping("/deleteRole")
    public R deleteRole(SysRole sysRole) {

        sysRoleService.deleteRole(sysRole);
        return R.ok();
    }

    @GetMapping("/getRoleByRoleId")
//    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SETTINGS_ROLE_LIST)
    public R getRoleByRoleId(@VerifyParam(required = true) Integer roleId) {
        SysRole sysRole = sysRoleService.getSysRoleByRoleId(roleId);
        return R.ok().data(sysRole);
    }


}

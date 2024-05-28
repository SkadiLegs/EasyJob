package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.SysRole2Menu;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysRole2MenuService
 * @MethodName
 * @Params
 */
public interface SysRole2MenuService extends IService<SysRole2Menu> {
    List<Integer> selectMenuIdsByRoleIds(Integer roleId);
}

package com.neo.common.service;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysRole2MenuService
 * @MethodName
 * @Params
 */
public interface SysRole2MenuService {
    List<Integer> selectMenuIdsByRoleIds(Integer roleId);
}

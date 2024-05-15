package com.neo.common.entity.po;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


/**
 * 角色对应的菜单权限表
 */
@Data
@AllArgsConstructor
public class SysRole2Menu implements Serializable {


    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 0:半选 1:全选
     */
    private Integer checkType;


}

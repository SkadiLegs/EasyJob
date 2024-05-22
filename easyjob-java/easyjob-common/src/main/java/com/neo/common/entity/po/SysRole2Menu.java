package com.neo.common.entity.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


/**
 * 角色对应的菜单权限表
 */
@Data
@AllArgsConstructor
@TableName(value = "sys_role_2_menu")

public class SysRole2Menu implements Serializable {
    public SysRole2Menu(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * 角色ID
     */
    @TableId
    private Integer roleId;

    /**
     * 菜单ID
     */
    private Integer menuId;

    /**
     * 0:半选 1:全选
     */
    @TableField(exist = false)
    private Integer checkType;


}

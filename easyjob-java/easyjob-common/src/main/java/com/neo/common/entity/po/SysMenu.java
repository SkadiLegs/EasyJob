package com.neo.common.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.neo.common.annotation.VerifyParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 菜单表
 */
@Data
public class SysMenu implements Serializable {


    /**
     * menu_id，自增主键
     */
    @TableId(value = "menu_id")
    private Integer menuId;

    /**
     * 菜单名
     */
    @VerifyParam(required = true, max = 32)
    private String menuName;

    /**
     * 菜单类型 0：菜单 1：按钮
     */
    @VerifyParam(required = true)
    private Integer menuType;

    /**
     * 菜单跳转到的地址
     */
    private String menuUrl;

    /**
     * 上级菜单ID
     */
    @VerifyParam(required = true)
    private Integer pId;

    /**
     * 菜单排序
     */
    @VerifyParam(required = true)
    private Integer sort;

    /**
     * 权限编码
     */
    @VerifyParam(required = true, max = 50)
    private String permissionCode;

    /**
     * 图标
     */
    @VerifyParam(max = 50)
    private String icon;
    /**
     * 该字段不映射数据库
     */
    @TableField(exist = false)
    private List<SysMenu> children;


}

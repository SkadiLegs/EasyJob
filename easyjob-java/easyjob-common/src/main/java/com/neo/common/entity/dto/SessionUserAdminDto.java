package com.neo.common.entity.dto;

import com.neo.common.entity.vo.SysMenuVO;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 用户session 信息
 * @Author 程序员老罗
 * @Date 2023/9/16 18:15
 * @ClassName
 * @MethodName
 * @Params
 */
public class SessionUserAdminDto implements Serializable {

    private static final long serialVersionUID = 1690149993220674991L;
    
    private Integer userId;
    private String userName;
    private Boolean superAdmin;
    private List<SysMenuVO> menuList;
    private List<String> permissionCodeList;

    public List<String> getPermissionCodeList() {
        return permissionCodeList;
    }

    public void setPermissionCodeList(List<String> permissionCodeList) {
        this.permissionCodeList = permissionCodeList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public List<SysMenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenuVO> menuList) {
        this.menuList = menuList;
    }

}

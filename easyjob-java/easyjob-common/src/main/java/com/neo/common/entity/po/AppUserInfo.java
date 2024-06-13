package com.neo.common.entity.po;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neo.common.entity.enums.DateTimePatternEnum;
import com.neo.common.uilts.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 *
 */
@Data
@NoArgsConstructor
public class AppUserInfo implements Serializable {


    /**
     * 用户ID
     */
    @TableId
    private String userId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别 0:女 1:男
     */
    private Integer sex;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date joinTime;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date lastLoginTime;

    /**
     * 最后使用的设备ID
     */
    private String lastUseDeviceId;

    /**
     * 手机品牌
     */
    private String lastUseDeviceBrand;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 0:禁用 1:正常
     */
    private Integer status;


    @Override
    public String toString() {
        return "用户ID:" + (userId == null ? "空" : userId) + "，邮箱:" + (email == null ? "空" : email) + "，昵称:" + (nickName == null ? "空" : nickName) + "，头像:" + (avatar == null ? "空" : avatar) + "，密码:" + (password == null ? "空" : password) + "，性别 0:女 1:男:" + (sex == null ? "空" : sex) + "，创建时间:" + (joinTime == null ? "空" : DateUtil.format(joinTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "，最后登录时间:" + (lastLoginTime == null ? "空" : DateUtil.format(lastLoginTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "，最后使用的设备ID:" + (lastUseDeviceId == null ? "空" : lastUseDeviceId) + "，手机品牌:" + (lastUseDeviceBrand == null ? "空" : lastUseDeviceBrand) + "，最后登录IP:" + (lastLoginIp == null ? "空" : lastLoginIp) + "，0:禁用 1:正常:" + (status == null ? "空" : status);
    }
}

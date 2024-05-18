package com.neo.common.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.enums.VerifyRegexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 账号信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysAccount implements Serializable {


    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 手机号
     */
    @VerifyParam(required = true, regex = VerifyRegexEnum.PHONE)
    private String phone;

    /**
     * 用户名
     */
    @VerifyParam(required = true, max = 20)
    private String userName;

    /**
     * 密码
     */
    @JsonIgnore
    @VerifyParam(regex = VerifyRegexEnum.PASSWORD)
    private String password;

    /**
     * 职位
     */
    private String position;

    /**
     * 状态 0:禁用 1:启用
     */
    private Integer status;

    /**
     * 用户拥有的角色多个用逗号隔开
     */
    @VerifyParam(required = true)
    private String roles;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @TableField(exist = false)
    private String roleNames;


}

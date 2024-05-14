package com.neo.common.entity.po;

//import com.neo.annotation.VerifyParam;
//import com.neo.entity.enums.DateTimePatternEnum;
//import com.neo.entity.enums.VerifyRegexEnum;
//import com.neo.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 账号信息
 */
@Data
@AllArgsConstructor
public class SysAccount implements Serializable {


    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 手机号
     */
//    @VerifyParam(required = true, regex = VerifyRegexEnum.PHONE)
    private String phone;

    /**
     * 用户名
     */
//    @VerifyParam(required = true, max = 20)
    private String userName;

    /**
     * 密码
     */
    @JsonIgnore
//    @VerifyParam(regex = VerifyRegexEnum.PASSWORD)
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
//    @VerifyParam(required = true)
    private String roles;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String roleNames;


}

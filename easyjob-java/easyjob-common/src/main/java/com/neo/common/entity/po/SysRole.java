package com.neo.common.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.neo.common.annotation.VerifyParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 系统角色表
 */
@Data
@AllArgsConstructor
public class SysRole implements Serializable {


    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    @VerifyParam(required = true, max = 100)
    private String roleName;

    /**
     * 角色描述
     */
    @VerifyParam(max = 300)
    private String roleDesc;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    private List<Integer> menuIds;


}

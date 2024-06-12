package com.neo.common.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neo.common.entity.enums.DateTimePatternEnum;
import com.neo.common.uilts.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 设备信息
 */
@Data
public class AppDevice implements Serializable {


    /**
     * 设备ID
     */
    @TableId
    private String deviceId;

    /**
     * 手机品牌
     */
    private String deviceBrand;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 最后使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date lastUseTime;

    /**
     * ip
     */
    private String ip;


    @Override
    public String toString() {
        return "设备ID:" + (deviceId == null ? "空" : deviceId) + "，手机品牌:" + (deviceBrand == null ? "空" : deviceBrand) + "，创建时间:" + (createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "，最后使用时间:" + (lastUseTime == null ? "空" : DateUtil.format(lastUseTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "，ip:" + (ip == null ? "空" : ip);
    }
}

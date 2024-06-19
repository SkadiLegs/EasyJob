package com.neo.common.entity.po;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.neo.common.entity.enums.DateTimePatternEnum;
import com.neo.common.uilts.CommonUtils;
import com.neo.common.uilts.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * app发布
 */
@Data
public class AppUpdate implements Serializable {


    private static final long serialVersionUID = 3346259101374338244L;
    /**
     * 自增ID
     */
    @TableId
    private Integer id;

    /**
     * 版本号
     */
    private String version;

    /**
     * 更新描述
     */
    private String updateDesc;

    /**
     * 更新类型0:全更新 1:局部热更新
     */
    private Integer updateType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 0:未发布 1:灰度发布 2:全网发布
     */
    private Integer status;

    /**
     * 灰度设备ID
     */
    private String grayscaleDevice;
    @TableField(exist = false)
    private String[] updateDescArray;

    public String[] getUpdateDescArray() {
        if (!CommonUtils.isEmpty(updateDesc)) {
            return updateDesc.split("\\|");
        }
        return updateDescArray;
    }

    @Override
    public String toString() {
        return "自增ID:" + (id == null ? "空" : id) + "，版本号:" + (version == null ? "空" : version) + "，更新描述:" + (updateDesc == null ? "空" : updateDesc) + "，更新类型0:全更新 1:局部热更新:" + (updateType == null ? "空" : updateType) + "，创建时间:" + (createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "，0:未发布 1:灰度发布 2:全网发布:" + (status == null ? "空" : status) + "，灰度设备ID:" + (grayscaleDevice == null ? "空" : grayscaleDevice);
    }
}

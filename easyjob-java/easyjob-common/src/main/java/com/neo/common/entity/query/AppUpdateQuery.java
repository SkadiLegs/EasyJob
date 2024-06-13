package com.neo.common.entity.query;


/**
 * app发布参数
 */
public class AppUpdateQuery extends BaseParam {


    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 版本号
     */
    private String version;

    private String versionFuzzy;

    /**
     * 更新描述
     */
    private String updateDesc;

    private String updateDescFuzzy;

    /**
     * 更新类型0:全更新 1:局部热更新
     */
    private Integer updateType;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 0:未发布 1:灰度发布 2:全网发布
     */
    private Integer status;

    /**
     * 灰度设备ID
     */
    private String grayscaleDevice;

    private String grayscaleDeviceFuzzy;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersionFuzzy(String versionFuzzy) {
        this.versionFuzzy = versionFuzzy;
    }

    public String getVersionFuzzy() {
        return this.versionFuzzy;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getUpdateDesc() {
        return this.updateDesc;
    }

    public void setUpdateDescFuzzy(String updateDescFuzzy) {
        this.updateDescFuzzy = updateDescFuzzy;
    }

    public String getUpdateDescFuzzy() {
        return this.updateDescFuzzy;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public Integer getUpdateType() {
        return this.updateType;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeStart() {
        return this.createTimeStart;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getCreateTimeEnd() {
        return this.createTimeEnd;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setGrayscaleDevice(String grayscaleDevice) {
        this.grayscaleDevice = grayscaleDevice;
    }

    public String getGrayscaleDevice() {
        return this.grayscaleDevice;
    }

    public void setGrayscaleDeviceFuzzy(String grayscaleDeviceFuzzy) {
        this.grayscaleDeviceFuzzy = grayscaleDeviceFuzzy;
    }

    public String getGrayscaleDeviceFuzzy() {
        return this.grayscaleDeviceFuzzy;
    }

}

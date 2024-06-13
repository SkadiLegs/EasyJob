package com.neo.common.entity.query;


/**
 * 设备信息参数
 */
public class AppDeviceQuery extends BaseParam {


    /**
     * 设备ID
     */
    private String deviceId;

    private String deviceIdFuzzy;

    /**
     * 手机品牌
     */
    private String deviceBrand;

    private String deviceBrandFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 最后使用时间
     */
    private String lastUseTime;

    private String lastUseTimeStart;

    private String lastUseTimeEnd;

    /**
     * ip
     */
    private String ip;

    private String ipFuzzy;


    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceIdFuzzy(String deviceIdFuzzy) {
        this.deviceIdFuzzy = deviceIdFuzzy;
    }

    public String getDeviceIdFuzzy() {
        return this.deviceIdFuzzy;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceBrand() {
        return this.deviceBrand;
    }

    public void setDeviceBrandFuzzy(String deviceBrandFuzzy) {
        this.deviceBrandFuzzy = deviceBrandFuzzy;
    }

    public String getDeviceBrandFuzzy() {
        return this.deviceBrandFuzzy;
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

    public void setLastUseTime(String lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    public String getLastUseTime() {
        return this.lastUseTime;
    }

    public void setLastUseTimeStart(String lastUseTimeStart) {
        this.lastUseTimeStart = lastUseTimeStart;
    }

    public String getLastUseTimeStart() {
        return this.lastUseTimeStart;
    }

    public void setLastUseTimeEnd(String lastUseTimeEnd) {
        this.lastUseTimeEnd = lastUseTimeEnd;
    }

    public String getLastUseTimeEnd() {
        return this.lastUseTimeEnd;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIpFuzzy(String ipFuzzy) {
        this.ipFuzzy = ipFuzzy;
    }

    public String getIpFuzzy() {
        return this.ipFuzzy;
    }

}

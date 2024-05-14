package com.neo.common.entity.vo.app;

import java.io.Serializable;
import java.util.List;


/**
 * app发布
 */
public class AppUpdateVO implements Serializable {

    private static final long serialVersionUID = 4756060542150096340L;
    private Integer id;

    /**
     * 版本号
     */
    private String version;

    /**
     * 更新描述
     */
    private List<String> updateList;

    private Long size;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<String> updateList) {
        this.updateList = updateList;
    }
}

package com.neo.common.entity.dto;

import java.util.List;

public class ImportErrorItem {
    private Integer rowNum;
    private List<String> errorItemList;

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public List<String> getErrorItemList() {
        return errorItemList;
    }

    public void setErrorItemList(List<String> errorItemList) {
        this.errorItemList = errorItemList;
    }
}

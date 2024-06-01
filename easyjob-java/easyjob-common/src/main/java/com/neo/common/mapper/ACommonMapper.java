package com.neo.common.mapper;

import org.apache.ibatis.annotations.Param;

public interface ACommonMapper {

    Integer updateCount(
            @Param("tableName") String tableName,
            @Param("readCount") Integer readCount,
            @Param("collectCount") Integer collectCount,
            @Param("keyId") Integer keyId);
}

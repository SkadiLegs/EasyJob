package com.neo.common.mapper;

import com.neo.common.entity.po.ShareInfo;
import com.neo.common.entity.query.ShareInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShareInfoMapper extends MyBaseMapper<ShareInfo> {

    void updateByShareInfoId(@Param("shareIds") List<String> shareIds, @Param("query") ShareInfoQuery shareInfoQuery);
}

package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.ShareInfo;
import com.neo.common.entity.query.ShareInfoQuery;
import com.neo.common.entity.vo.PaginationResultVO;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName examQuestionService
 * @MethodName
 * @Params
 */
public interface ShareInfoService extends IService<ShareInfo> {
    PaginationResultVO selectPage(ShareInfoQuery query);

    void updateStatus(ShareInfoQuery shareInfoQuery, String shareIds);

    Integer saveShareInfo(ShareInfoQuery shareInfoQuery, Boolean superAdmin);

    void removeBatchShareInfo(String[] shareIds, Integer userId);
}

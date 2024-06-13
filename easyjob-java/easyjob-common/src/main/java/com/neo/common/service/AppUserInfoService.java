package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.AppUserInfo;
import com.neo.common.entity.query.AppUserInfoQuery;
import com.neo.common.entity.vo.PaginationResultVO;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/13
 * @ClassName AppUserInfoService
 * @MethodName
 * @Params
 */
public interface AppUserInfoService extends IService<AppUserInfo> {


    PaginationResultVO<AppUserInfo> findListByPage(AppUserInfoQuery query);


}

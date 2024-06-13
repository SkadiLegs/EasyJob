package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.AppDevice;
import com.neo.common.entity.query.AppDeviceQuery;
import com.neo.common.entity.vo.PaginationResultVO;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/13
 * @ClassName AppDeviceService
 * @MethodName
 * @Params
 */
public interface AppDeviceService extends IService<AppDevice> {


    PaginationResultVO<AppDevice> findListByPage(AppDeviceQuery query);
}

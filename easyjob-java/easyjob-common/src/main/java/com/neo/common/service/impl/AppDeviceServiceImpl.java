package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.po.AppDevice;
import com.neo.common.entity.query.AppDeviceQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.mapper.AppDeviceMapper;
import com.neo.common.service.AppDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/13
 * @ClassName AppDeviceServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class AppDeviceServiceImpl extends ServiceImpl<AppDeviceMapper, AppDevice> implements AppDeviceService {

    @Resource
    AppDeviceMapper appDeviceMapper;


    @Override
    public PaginationResultVO<AppDevice> findListByPage(AppDeviceQuery query) {
        QueryWrapper<AppDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(query.getOrderByDesc());
        if (query.getDeviceBrandFuzzy() != null) {
            queryWrapper.like("device_brand", query.getDeviceBrandFuzzy());
        }
        if (query.getDeviceIdFuzzy() != null) {
            queryWrapper.like("ip", query.getDeviceIdFuzzy());
        }
        if (query.getCreateTimeStart() != null && query.getCreateTimeEnd() != null) {
            queryWrapper.between("create_time", query.getCreateTimeStart(), query.getCreateTimeEnd());
        }
        if (query.getLastUseTimeStart() != null && query.getLastUseTimeEnd() != null) {
            queryWrapper.between("last_use_time", query.getLastUseTimeStart(), query.getLastUseTimeEnd());
        }

        Page<AppDevice> page = new Page(query.getPageNo() == null ? 0 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        Page<AppDevice> appDevicePage = appDeviceMapper.selectPage(page, queryWrapper);
        PaginationResultVO<AppDevice> paginationResultVO = new PaginationResultVO<>(
                (int) appDevicePage.getTotal(),
                (int) appDevicePage.getSize(),
                (int) appDevicePage.getCurrent(),
                (int) appDevicePage.getPages(),
                appDevicePage.getRecords());

        return paginationResultVO;
    }
}

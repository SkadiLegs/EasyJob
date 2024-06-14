package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.po.AppUserInfo;
import com.neo.common.entity.query.AppUserInfoQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.mapper.AppUserInfoMapper;
import com.neo.common.service.AppUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/13
 * @ClassName AppUserInfoServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class AppUserInfoServiceImpl extends ServiceImpl<AppUserInfoMapper, AppUserInfo> implements AppUserInfoService {
    @Resource
    AppUserInfoMapper appUserInfoMapper;


    @Override
    public PaginationResultVO<AppUserInfo> findListByPage(AppUserInfoQuery query) {
        Page<AppUserInfo> page = new Page<>(query.getPageNo() == null ? 0 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        QueryWrapper<AppUserInfo> queryWrapper = new QueryWrapper();
        if (query.getJoinTimeStart() != null && query.getJoinTimeEnd() != null) {
            queryWrapper.between("join_time", query.getJoinTimeStart(), query.getJoinTimeEnd());
        }
        if (query.getEmailFuzzy() != null) {
            queryWrapper.like("email", query.getEmailFuzzy());
        }
        if (query.getLastUseDeviceIdFuzzy() != null) {
            queryWrapper.like("last_use_device_id", query.getLastUseDeviceIdFuzzy());
        }
        queryWrapper.orderByDesc(query.getOrderByDesc());
        Page<AppUserInfo> pageAppUserInfo = appUserInfoMapper.selectPage(page, queryWrapper);
        PaginationResultVO<AppUserInfo> paginationResultVO = new PaginationResultVO<>(
                (int) pageAppUserInfo.getTotal(),
                (int) pageAppUserInfo.getSize(),
                (int) pageAppUserInfo.getCurrent(),
                (int) pageAppUserInfo.getPages(),
                pageAppUserInfo.getRecords());
        return paginationResultVO;
    }


}

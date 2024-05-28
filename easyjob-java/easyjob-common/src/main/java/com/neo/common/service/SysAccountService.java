package com.neo.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.po.SysAccount;
import com.neo.common.entity.query.SysAccountQuery;

public interface SysAccountService extends IService<SysAccount> {

    //登陆
    public SessionUserAdminDto login(String phone, String password);

    Page<SysAccount> selectByPage(SysAccountQuery sysAccountQuery);

    SysAccount getSysAccountByUserId(Integer userId);

    void saveAccountUser(SysAccount sysAccount);


}

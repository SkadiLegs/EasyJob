package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.po.SysAccount;

public interface SysAccountService extends IService<SysAccount> {

    //登陆
    public SessionUserAdminDto login(String phone, String password);

}

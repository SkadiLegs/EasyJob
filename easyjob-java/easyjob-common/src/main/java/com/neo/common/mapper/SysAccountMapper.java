package com.neo.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neo.common.entity.po.SysAccount;

public interface SysAccountMapper extends BaseMapper<SysAccount> {
    SysAccount selectAccountTest(int id);
}

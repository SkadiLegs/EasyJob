package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.enums.SysAccountStatusEnum;
import com.neo.common.entity.po.SysAccount;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.SysAccountMapper;
import com.neo.common.service.SysAccountService;
import com.neo.common.uilts.MD5;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;

@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {


    @Override
    public void login(String phone, String password) {
        //判断空值和密码正确、用户是否被禁用
        try {
            SysAccount sysUser = baseMapper.selectOne(new QueryWrapper<SysAccount>().eq("phone", phone));
            if (sysUser.getStatus().equals(SysAccountStatusEnum.DISABLE)) {
                throw new EasyJobException(ResultCode.ERROR_OTHER, "用户已被禁用");
            }
            if (MD5.encrypt(password).equals(sysUser.getPassword())) {
                throw new EasyJobException(ResultCode.ERROR_OTHER, "账号或者密码错误");
            }
            //
            SessionUserAdminDto adminDto = new SessionUserAdminDto();
            adminDto.setUserId(sysUser.getUserId());
            adminDto.setUserName(sysUser.getUserName());


        } catch (NullPointerException e) {
            throw new EasyJobException(ResultCode.NOT_FOUND, "用户未注册");
        } catch (Exception e) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "未知异常");
        }


    }
}

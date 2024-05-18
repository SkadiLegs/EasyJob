package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.enums.SysAccountStatusEnum;
import com.neo.common.entity.po.SysAccount;
import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysMenuQuery;
import com.neo.common.entity.vo.SysMenuVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.SysAccountMapper;
import com.neo.common.service.SysAccountService;
import com.neo.common.service.SysMenuService;
import com.neo.common.uilts.CopyTools;
import com.neo.common.uilts.MD5;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {
    @Resource
    SysMenuService sysMenuService;

    @Override
    public SessionUserAdminDto login(String phone, String password) {
        //判断空值和密码正确、用户是否被禁用
        try {
            SysAccount sysUser = baseMapper.selectOne(new QueryWrapper<SysAccount>().eq("phone", phone));
            if (sysUser.getStatus().equals(SysAccountStatusEnum.DISABLE)) {
                throw new EasyJobException(ResultCode.ERROR_OTHER, "用户已被禁用");
            }
            if (MD5.encrypt(password).equals(sysUser.getPassword())) {
                throw new EasyJobException(ResultCode.ERROR_OTHER, "账号或者密码错误");
            }
            // 查询所有表sys_menu中所有数据
            SysMenuQuery query = new SysMenuQuery();
            query.setOrderBy("sort");
            // 这条数据是为了不将数据放到"所有菜单"下
            query.setFormate2Tree(false);
            List<SysMenu> sysMenuList = sysMenuService.findLisByParam(query);
            // 递归排序
            sysMenuList = sysMenuService.convertLine2Tree4Menu(sysMenuList, 0);
            List<SysMenuVO> menuListVO = new ArrayList<>();

            List<String> permissionCodeList = new ArrayList<>();
            // 获取数据库中的接口地址
            sysMenuList.forEach(item -> {
                permissionCodeList.add(item.getPermissionCode());
            });
            // 将数据放到SysMenuVO中,以便返回前端
            sysMenuList.forEach(item -> {
                SysMenuVO menuVO = CopyTools.copy(item, SysMenuVO.class);
                menuVO.setChildren(CopyTools.copyList(item.getChildren(), SysMenuVO.class));
                menuListVO.add(menuVO);
            });

            SessionUserAdminDto adminDto = new SessionUserAdminDto();
            adminDto.setUserId(sysUser.getUserId());
            adminDto.setUserName(sysUser.getUserName());
            adminDto.setPermissionCodeList(permissionCodeList);
            adminDto.setMenuList(menuListVO);

            return adminDto;
        } catch (NullPointerException e) {
            throw new EasyJobException(ResultCode.NOT_FOUND, "用户未注册");
        } catch (Exception e) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "未知异常");
        }


    }
}

package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.config.AppConfig;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.enums.MenuTypeEnum;
import com.neo.common.entity.enums.SysAccountStatusEnum;
import com.neo.common.entity.enums.UserStatusEnum;
import com.neo.common.entity.po.SysAccount;
import com.neo.common.entity.po.SysMenu;
import com.neo.common.entity.query.SysAccountQuery;
import com.neo.common.entity.query.SysMenuQuery;
import com.neo.common.entity.vo.SysMenuVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.SysAccountMapper;
import com.neo.common.service.SysAccountService;
import com.neo.common.service.SysMenuService;
import com.neo.common.uilts.CopyTools;
import com.neo.common.uilts.MD5;
import com.neo.common.uilts.ResultCode;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {
    @Resource
    SysMenuService sysMenuService;

    @Resource
    AppConfig appConfig;

    @Override
    public SessionUserAdminDto login(String phone, String password) {
        //判断空值和密码正确、用户是否被禁用
        try {
            SysAccount sysUser = baseMapper.selectOne(new QueryWrapper<SysAccount>().eq("phone", phone));
            if (sysUser.getStatus().equals(SysAccountStatusEnum.DISABLE.getStatus())) {
                throw new EasyJobException(ResultCode.ERROR_OTHER, "用户已被禁用");
            }
            if (MD5.encrypt(password).equals(sysUser.getPassword())) {
                throw new EasyJobException(ResultCode.ERROR_OTHER, "账号或者密码错误");
            }

            SessionUserAdminDto adminDto = new SessionUserAdminDto();
            adminDto.setUserId(sysUser.getUserId());
            adminDto.setUserName(sysUser.getUserName());

            List<SysMenu> sysMenuList;
            String[] split = appConfig.getSuperAdminPhones().split(",");
            boolean contains = ArrayUtils.contains(appConfig.getSuperAdminPhones().split(","), phone);
            // 如果是超级管理员则获取所有信息
            if (StringUtils.hasText(appConfig.getSuperAdminPhones())
                    && ArrayUtils.contains(appConfig.getSuperAdminPhones().split(","), phone)) {
                adminDto.setSuperAdmin(true);
                // 查询所有表sys_menu中所有数据
                SysMenuQuery query = new SysMenuQuery();
                query.setOrderByAsc("sort");
                // 这条数据是为了不将数据放到"所有菜单"下
                query.setFormate2Tree(false);
                sysMenuList = sysMenuService.findLisByParam(query);
            } else {
                adminDto.setSuperAdmin(false);
                //根据权限获取menu列表
                sysMenuList = sysMenuService.getAllMenuByRoleIds(sysUser.getRoles());
            }

            List<String> permissionCodeList = new ArrayList<>();
            List<SysMenu> menuList = new ArrayList<>();
            // 区分菜单和按钮,如果是菜单则加入到menuList中
            sysMenuList.forEach(item -> {
                if (MenuTypeEnum.MEMU.getType().equals(item.getMenuType())) {
                    menuList.add(item);
                }
                // 获取所有权限编码
                permissionCodeList.add(item.getPermissionCode());
            });
            //如果在menuList完成后还是为空则说明没有权限
            if (menuList.isEmpty()) {
                throw new EasyJobException(ResultCode.NOT_FOUND, "请联系管理员分配角色");
            }
            // 递归排序
            sysMenuList = sysMenuService.convertLine2Tree4Menu(sysMenuList, 0);

            List<SysMenuVO> menuListVO = new ArrayList<>();
            // 将数据放到menuListVO中,以便返回前端
            sysMenuList.forEach(item -> {
                SysMenuVO menuVO = CopyTools.copy(item, SysMenuVO.class);
                menuVO.setChildren(CopyTools.copyList(item.getChildren(), SysMenuVO.class));
                menuListVO.add(menuVO);
            });

            adminDto.setPermissionCodeList(permissionCodeList);
            adminDto.setMenuList(menuListVO);
            return adminDto;
        } catch (NullPointerException e) {
            throw new EasyJobException(ResultCode.NOT_FOUND, "用户未注册");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EasyJobException(ResultCode.ERROR_OTHER, "未知异常");
        }
    }

    @Override
    public Page<SysAccount> selectByPage(SysAccountQuery sysAccountQuery) {
        Page<SysAccount> page = new Page(sysAccountQuery.getPageNo() == null ? 1 : sysAccountQuery.getPageNo(), sysAccountQuery.getPageSize() == null ? 15 : sysAccountQuery.getPageSize());
        QueryWrapper<SysAccount> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc(sysAccountQuery.getOrderByDesc());
        if (sysAccountQuery.getPhoneFuzzy() != null) {
            queryWrapper.like("phone", sysAccountQuery.getPhoneFuzzy());
        }
        if (sysAccountQuery.getUserNameFuzzy() != null) {
            queryWrapper.like("user_name", sysAccountQuery.getUserNameFuzzy());
        }

        Page<SysAccount> sysAccountPage = baseMapper.selectPage(page, queryWrapper);
        return sysAccountPage;
    }

    @Override
    public SysAccount getSysAccountByUserId(Integer userId) {
        return baseMapper.selectById(userId);
    }

    @Override
    public void saveAccountUser(SysAccount sysAccount) {
        SysAccount selectByPhone_Account = baseMapper.selectOne(new QueryWrapper<SysAccount>().eq("phone", sysAccount.getPhone()));
        // 如果根据手机查询数据库结果不为空,且(传入的UserId为空 或 如果根据手机查询数据库结果的 UserId 不等于 前端传入的值的UserId)
        if (selectByPhone_Account != null
                && (sysAccount.getUserId() == null || !selectByPhone_Account.getUserId().equals(sysAccount.getUserId()))) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "该手机号已经被使用");
        }
        if (sysAccount.getUserId() == null) {
            sysAccount.setStatus(UserStatusEnum.ENABLE.getStatus());
            sysAccount.setPassword(MD5.encrypt(sysAccount.getPassword()));
            Integer insert = baseMapper.insert(sysAccount);
        } else {
            sysAccount.setStatus(null);
            sysAccount.setPassword(null);
            baseMapper.updateById(sysAccount);
        }
    }


}

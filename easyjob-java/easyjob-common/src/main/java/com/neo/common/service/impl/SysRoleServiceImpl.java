package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.po.SysRole;
import com.neo.common.entity.query.SysRoleQuery;
import com.neo.common.mapper.SysRoleMapper;
import com.neo.common.service.SysRole2MenuService;
import com.neo.common.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysRoleService
 * @MethodName
 * @Params
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    SysRole2MenuService sysRole2MenuService;

    @Override
    public void saveRole(SysRole sysRole) {
        if (sysRole.getRoleId() == null) {
            baseMapper.insert(sysRole);
        } else {
            baseMapper.updateById(sysRole);
        }
    }

    @Override
    public void deleteRole(SysRole sysRole) {
        baseMapper.deleteById(sysRole);
    }

    @Override
    public List<SysRole> findListByPage(SysRoleQuery query) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc(query.getOrderBy());

        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据RoleId获取对象
     */
    @Override
    public SysRole getSysRoleByRoleId(Integer roleId) {
        SysRole sysRole = baseMapper.selectById(roleId);
        List<Integer> selectMenuIds = sysRole2MenuService.selectMenuIdsByRoleIds(roleId);
        sysRole.setMenuIds(selectMenuIds);
        return sysRole;
    }
}

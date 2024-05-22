package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.po.SysRole2Menu;
import com.neo.common.mapper.SysRole2MenuMapper;
import com.neo.common.service.SysRole2MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/15
 * @ClassName SysRole2MenuService
 * @MethodName
 * @Params
 */

@Service
public class SysRole2MenuServiceImpl extends ServiceImpl<SysRole2MenuMapper, SysRole2Menu> implements SysRole2MenuService {

    @Override
    public List<Integer> selectMenuIdsByRoleIds(Integer roleId) {

        List<SysRole2Menu> selectMenuIds = baseMapper.selectList(new QueryWrapper<SysRole2Menu>().select("menu_id").eq("role_id", roleId));
        List<Integer> MenuIds = new ArrayList<>();
        for (SysRole2Menu tempSysRole2Menu : selectMenuIds) {
            MenuIds.add(tempSysRole2Menu.getMenuId());
        }
        return MenuIds;
    }
}

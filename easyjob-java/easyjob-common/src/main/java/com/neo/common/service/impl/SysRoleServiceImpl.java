package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.MenuCheckTypeEnum;
import com.neo.common.entity.po.SysAccount;
import com.neo.common.entity.po.SysRole;
import com.neo.common.entity.po.SysRole2Menu;
import com.neo.common.entity.query.SysRoleQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.SysAccountMapper;
import com.neo.common.mapper.SysRole2MenuMapper;
import com.neo.common.mapper.SysRoleMapper;
import com.neo.common.service.SysRole2MenuService;
import com.neo.common.service.SysRoleService;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    SysRole2MenuMapper sysRole2MenuMapper;
    @Resource
    SysRole2MenuService sysRole2MenuService;
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    SysAccountMapper sysAccountMapper;


    /**
     * @Description 保存角色
     * @Author Lenove
     * @Date 2024/5/23
     * @MethodName saveRole
     * @Param sysRole:角色id;
     * menuIds多个menuId构成的字符串;
     * halfMenuIds是否全选,因为给角色分配menu权限时menu是一个树的形式,勾选父选项可以选择父选项下所有的字选项
     * @Return: null
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRole sysRole, String menuIds, String halfMenuIds) {
        Integer roleId = sysRole.getRoleId();
        Boolean addMenu = false;
        if (sysRole.getRoleId() == null) {
            baseMapper.insert(sysRole);
            roleId = sysRole.getRoleId();
            addMenu = true;
        } else {
            baseMapper.updateById(sysRole);
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_name", sysRole.getRoleName());
        Integer roleCount = Math.toIntExact(baseMapper.selectCount(queryWrapper));
        if (roleCount > 1) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "角色已经存在");
        }
        //到menu和role的连接表role_2_menu中添加权限
        if (addMenu) {
            saveRoleMenu(roleId, menuIds, halfMenuIds);
        }
    }

    /**
     * @Description 更改角色权限
     * @Author Lenove
     * @Date 2024/5/23
     * @MethodName saveRoleMenu
     * @Param null
     * @Return: null
     */
    //TODO menuIds出现空指针
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenu(Integer roleId, String menuIds, String halfMenuIds) {
        if (null == roleId || menuIds == null) {
            System.out.println("********roleId********" + roleId);
            System.out.println("********menuIds********" + menuIds);
            throw new EasyJobException(ResultCode.ERROR_NAN, "roleId空指针");
        }
        List<SysRole2Menu> role2MenuList = new ArrayList<>();
        sysRole2MenuMapper.deleteById(roleId);
        // 返回menuId的字符串.用","分隔,打断其使其成为列表
        String[] menuIdsArray = menuIds.split(",");
        String[] halfMenuIdArray = !StringUtils.hasText(halfMenuIds) ? new String[]{} : halfMenuIds.split(",");
        //MenuCheckTypeEnum.ALL 代表全选
        convertMenuId2RoleMenu(role2MenuList, roleId, menuIdsArray, MenuCheckTypeEnum.ALL);
        //MenuCheckTypeEnum.HALF 代表半选
        convertMenuId2RoleMenu(role2MenuList, roleId, halfMenuIdArray, MenuCheckTypeEnum.HALF);
        //不为空则批量存入role2Menu
        if (!role2MenuList.isEmpty()) {
            sysRole2MenuService.saveBatch(role2MenuList);
        }
    }

    //TODO 检查此处返回值格式
    @Override
    public Page<SysRole> findListByParam(SysRoleQuery param) {
        Page<SysRole> page = Page.of(1, 10);
        QueryWrapper queryWrapper = new QueryWrapper();
        if (param.getRoleNameFuzzy() != null) {
            queryWrapper.like("role_name", param.getRoleNameFuzzy());
        }
        if (param.getRoleDescFuzzy() != null) {
            queryWrapper.like("role_desc", param.getRoleDescFuzzy());
        }
        queryWrapper.orderByDesc(param.getOrderByDesc());
        Page selectPage = sysRoleMapper.selectPage(page, queryWrapper);
        return selectPage;
    }

    /**
     * @Description 将menuIdArray中的menuid存入role2Menu中并根据设置role2Menu的CheckType属性, 方便后续吧role2Menu存入数据库
     * @Author Lenove
     * @Date 2024/5/23
     * @MethodName checkTypeEnum:检查全选or半选
     * @Param null
     * @Return: null
     */
    private void convertMenuId2RoleMenu(List<SysRole2Menu> role2MenuList,
                                        Integer roleId,
                                        String[] menuIdArray,
                                        MenuCheckTypeEnum checkTypeEnum) {
        for (String menuId : menuIdArray) {
            SysRole2Menu role2Menu = new SysRole2Menu();
            role2Menu.setMenuId(Integer.parseInt(menuId));
            role2Menu.setRoleId(roleId);
            role2Menu.setCheckType(checkTypeEnum.getType());

            role2MenuList.add(role2Menu);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteRole(Integer roleId) {
        System.out.println("deleteRole" + roleId);
        Long roles = sysAccountMapper.selectCount(new QueryWrapper<SysAccount>().like("roles", roleId));
        if (roles > 0) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "角色已被用户使用,请先更改用户角色");
        }
        int deleteById = baseMapper.deleteById(roleId);
        sysRole2MenuMapper.delete(new QueryWrapper<SysRole2Menu>().eq("role_id", roleId));
        return deleteById;
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(SysRoleQuery param) {
        return Math.toIntExact(this.sysRoleMapper.selectCount(new QueryWrapper<>()));
    }


    @Override
    public PaginationResultVO<SysRole> findListByPage(SysRoleQuery query) {
        Integer countByParam = this.findCountByParam(query);
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasText(query.getRoleDescFuzzy())) {
            queryWrapper.like("role_desc", query.getRoleDescFuzzy());
        }
        if (StringUtils.hasText(query.getRoleNameFuzzy())) {
            queryWrapper.like("role_name", query.getRoleNameFuzzy());
        }
        queryWrapper.orderByDesc(query.getOrderByDesc());
        Page<SysRole> page = new Page<>(1, query.getPageSize() == null ? 15 : query.getPageSize());//当前页码为1,显示15条
        Page<SysRole> sysRolePage = sysRoleMapper.selectPage(page, queryWrapper);
        PaginationResultVO<SysRole> paginationResultVO = new PaginationResultVO<>(countByParam, (int) sysRolePage.getSize(), (int) sysRolePage.getCurrent(), (int) sysRolePage.getPages(), sysRolePage.getRecords());

        return paginationResultVO;
    }

    /**
     * 根据RoleId获取对象
     */
    @Override
    public SysRole getSysRoleByRoleId(Integer roleId) {
        SysRole sysRole = baseMapper.selectById(roleId);
        QueryWrapper<SysRole2Menu> queryWrapper = new QueryWrapper();
        queryWrapper.select("menu_id").eq("role_id", roleId).eq("check_type", 1);
        List<SysRole2Menu> selectMenuIds = sysRole2MenuMapper.selectList(queryWrapper);
        List<Integer> MenuIds = new ArrayList<>();
        selectMenuIds.forEach(item -> {
            MenuIds.add(item.getMenuId());
        });
        sysRole.setMenuIds(MenuIds);
        return sysRole;
    }

}

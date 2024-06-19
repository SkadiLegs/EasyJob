package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.config.AppConfig;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.enums.AppUpdateSatusEnum;
import com.neo.common.entity.enums.AppUpdateTypeEnum;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.po.AppUpdate;
import com.neo.common.entity.query.AppUpdateQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.AppUpdateMapper;
import com.neo.common.service.AppUpdateService;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/19
 * @ClassName AppUpdateServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class AppUpdateServiceImpl extends ServiceImpl<AppUpdateMapper, AppUpdate> implements AppUpdateService {
    @Resource
    AppUpdateMapper appUpdateMapper;

    @Resource
    AppConfig appConfig;

    @Override
    public PaginationResultVO getAppUpdatePage(AppUpdateQuery query) {
        Page<AppUpdate> page = new Page<>(query.getPageNo() != null ? query.getPageNo() : 0, query.getPageSize() != null ? query.getPageSize() : PageSize.SIZE15.getSize());
        QueryWrapper<AppUpdate> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc(query.getOrderByDesc());
        if (query.getCreateTimeStart() != null && query.getCreateTimeEnd() != null) {
            queryWrapper.between("create_time", query.getCreateTimeStart(), query.getCreateTimeEnd());
        }
        Page<AppUpdate> appUpdatePage = appUpdateMapper.selectPage(page, queryWrapper);
        PaginationResultVO<AppUpdate> paginationResultVO = new PaginationResultVO<>(
                (int) appUpdatePage.getTotal(),
                (int) appUpdatePage.getSize(),
                (int) appUpdatePage.getCurrent(),
                (int) appUpdatePage.getPages(),
                appUpdatePage.getRecords());
        return paginationResultVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateAppUpdate(AppUpdate appUpdate, MultipartFile file) {
        QueryWrapper<AppUpdate> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("id");
        Page<AppUpdate> page = new Page<>(0, 1);
        AppUpdate appUpdateMaxVersion = appUpdateMapper.selectPage(page, queryWrapper).getRecords().get(0);
        if (appUpdateMaxVersion != null) {
            Long replaceVersionDB = Long.parseLong(appUpdateMaxVersion.getVersion().replace(".", ""));
            Long saveVersion = Long.parseLong(appUpdate.getVersion().replace(".", ""));
            if (appUpdate.getId() != null && (replaceVersionDB > saveVersion || appUpdate.getVersion().equals(appUpdateMaxVersion.getVersion()))) {
                throw new EasyJobException(ResultCode.VERSION_ERROR, "更新或新版本必须大于历史版本");
            }
        }
        if (appUpdate.getId() == null) {
            appUpdate.setStatus(AppUpdateSatusEnum.INIT.getStatus());
            appUpdateMapper.insert(appUpdate);
        } else {
            appUpdate.setStatus(null);
            appUpdate.setGrayscaleDevice(null);
            appUpdateMapper.updateById(appUpdate);
        }
        if (file != null) {
            File folder = new File(appConfig.getProjectFolder() + Constants.APP_UPDATE_FOLDER);
            if (folder.exists()) {
                folder.mkdir();
            }
            AppUpdateTypeEnum appUpdateTypeEnum = AppUpdateTypeEnum.getByType(appUpdate.getUpdateType());
            try {
                if (appUpdateTypeEnum == null) {
                    throw new EasyJobException(ResultCode.ERROR_NAN, "app更新指定参数不存在");
                }
                // 将上传的文件转移到指定的新文件位置
                file.transferTo(new File(folder.getAbsolutePath() + "/" + appUpdate.getId() + appUpdateTypeEnum.getSuffix()));
            } catch (IOException e) {
                throw new EasyJobException(ResultCode.APPUPDATE_BAD, "App更新失败");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postAppUpdata(Integer id, Integer status, String grayscaleDevice) {
        AppUpdateSatusEnum statusEnum = AppUpdateSatusEnum.getByStatus(status);

        if (id == null) {
            throw new EasyJobException(ResultCode.ERROR_NAN, "发布id为空");
        }
        if (statusEnum == AppUpdateSatusEnum.GRAYSCALE && StringUtils.isEmpty(grayscaleDevice)) {
            throw new EasyJobException(ResultCode.ERROR_600, "发布app参数错误");
        }
        if (AppUpdateSatusEnum.GRAYSCALE != statusEnum) {
            grayscaleDevice = "";
        }
        AppUpdate appUpdate = new AppUpdate();
        appUpdate.setGrayscaleDevice(grayscaleDevice);
        appUpdate.setId(id);
        appUpdate.setStatus(status);
        appUpdateMapper.updateById(appUpdate);
    }

    @Override
    public void removeAppUpdataByid(Integer id) {
        appUpdateMapper.deleteById(id);
    }
}

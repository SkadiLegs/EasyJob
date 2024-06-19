package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.AppUpdate;
import com.neo.common.entity.query.AppUpdateQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/19
 * @ClassName AppUpdateService
 * @MethodName
 * @Params
 */
public interface AppUpdateService extends IService<AppUpdate> {
    PaginationResultVO getAppUpdatePage(AppUpdateQuery query);

    void saveOrUpdateAppUpdate(AppUpdate appUpdate, MultipartFile file);

    void postAppUpdata(Integer id, Integer status, String grayscaleDevice);

    void removeAppUpdataByid(Integer id);
}

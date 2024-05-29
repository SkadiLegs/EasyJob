package com.neo.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.po.ShareInfo;
import com.neo.common.mapper.ShareInfoMapper;
import com.neo.common.service.ShareInfoService;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName ShareInfoServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class ShareInfoServiceImpl extends ServiceImpl<ShareInfoMapper, ShareInfo> implements ShareInfoService {
}

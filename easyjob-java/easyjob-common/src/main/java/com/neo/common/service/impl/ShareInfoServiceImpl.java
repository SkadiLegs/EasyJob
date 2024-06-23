package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.po.ShareInfo;
import com.neo.common.entity.query.ShareInfoQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.ACommonMapper;
import com.neo.common.mapper.ShareInfoMapper;
import com.neo.common.service.ShareInfoService;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    ShareInfoMapper shareInfoMapper;

    @Resource
    ACommonMapper aCommonMapper;

    @Override
    public PaginationResultVO selectPage(ShareInfoQuery query) {
        Page<ShareInfo> page = new Page(query.getPageNo() == null ? 0 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        QueryWrapper<ShareInfo> queryWrapper = new QueryWrapper();
        if (!query.getQueryTextContent()) {
            queryWrapper.select(ShareInfo.class, tableFieldInfo -> !tableFieldInfo.getColumn().equals("content"));
        }
        if (query.getTitleFuzzy() != null) {
            queryWrapper.like("title", query.getTitleFuzzy());
        }
        if (query.getCreateUserNameFuzzy() != null) {
            queryWrapper.like("create_user_name", query.getCreateUserNameFuzzy());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq("status", query.getStatus());
        }
        queryWrapper.orderByDesc(query.getOrderByDesc());
        Page<ShareInfo> shareInfoPage = shareInfoMapper.selectPage(page, queryWrapper);
        PaginationResultVO<ShareInfo> paginationResultVO = new PaginationResultVO<>(
                (int) shareInfoPage.getTotal(),
                (int) shareInfoPage.getSize(),
                (int) shareInfoPage.getCurrent(),
                (int) shareInfoPage.getPages(),
                shareInfoPage.getRecords());
        return paginationResultVO;
    }


    @Override
    public void updateStatus(ShareInfoQuery shareInfoQuery, String shareIds) {
        List<String> shareId_list = Arrays.asList(shareIds.split(","));
        QueryWrapper<ShareInfo> queryWrapper = new QueryWrapper();
        queryWrapper.in("share_id", shareId_list);
        shareInfoMapper.updateByShareInfoId(shareId_list, shareInfoQuery);
    }

    @Override
    public Integer saveShareInfo(ShareInfoQuery shareInfoQuery, Boolean superAdmin) {
        int saveDateNum = 0;

        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setTitle(shareInfoQuery.getTitle());
        shareInfo.setCoverPath(shareInfoQuery.getCoverPath());
        shareInfo.setCoverType(shareInfoQuery.getCoverType());
        shareInfo.setCreateUserId(shareInfoQuery.getCreateUserId());
        shareInfo.setContent(shareInfoQuery.getContent());
        shareInfo.setCreateUserName(shareInfoQuery.getCreateUserName());
        if (shareInfoQuery.getShareId() == null) {
            saveDateNum = shareInfoMapper.insert(shareInfo);
        } else {
            ShareInfo dbShareInfo = shareInfoMapper.selectById(shareInfoQuery.getShareId());
            if (shareInfoQuery.getCreateUserId().equals(dbShareInfo.getCreateUserId()) && !superAdmin) {
                throw new EasyJobException(ResultCode.ERROR_600, "该用户无法修改其他用户的数据");
            }
            shareInfo.setShareId(shareInfoQuery.getShareId());
            saveDateNum = shareInfoMapper.updateById(shareInfo);
        }

        return saveDateNum;
    }

    @Override
    public void removeBatchShareInfo(String[] shareIds, Integer userid) {
        List<String> ShareIds_list = Arrays.asList(shareIds);
        QueryWrapper<ShareInfo> queryWrapper = new QueryWrapper();
        queryWrapper.select("share_id", "create_user_id", "create_user_name");
        queryWrapper.in("share_id", ShareIds_list);
        List<ShareInfo> dbShareInfo = shareInfoMapper.selectList(queryWrapper);
        if (userid != null) {
            List<ShareInfo> badShareInfo = dbShareInfo.stream().filter(item -> !item.getCreateUserId().equals(String.valueOf(userid))).collect(Collectors.toList());
            if (!badShareInfo.isEmpty()) {
                throw new EasyJobException(ResultCode.ERROR_NOPERMISSION, "删除的队列中存在非该用户创建的数据");
            }
        }
        shareInfoMapper.deleteBatchIds(ShareIds_list);
    }

    @Override
    public ShareInfo showDetailNext(ShareInfoQuery query, Integer nextType, Integer currentId, boolean updateReadCount) {
//        if (nextType == null) {
//            query.setShareId(currentId);
//        } else {
//            query.setNextType(nextType);
//            query.setCurrentId(currentId);
//        }
        // 获取问题详情
        QueryWrapper<ShareInfo> queryWrapper = new QueryWrapper();
        if (nextType == null) {
            queryWrapper.eq("share_id", currentId);
        } else if (nextType == 1) {
            queryWrapper.lt("share_id", currentId);
            queryWrapper.orderByDesc("share_id");
        } else if (nextType == -1) {
            queryWrapper.gt("share_id", currentId);
            queryWrapper.orderByAsc("share_id");
        }
        queryWrapper.last("limit 0,1");
        ShareInfo shareInfo = shareInfoMapper.selectOne(queryWrapper);
        if (shareInfo == null && nextType == null) {
            throw new EasyJobException(ResultCode.NOT_FOUND, "内容不存在");
        } else if (shareInfo == null && nextType == -1) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "已经是第一条了");
        } else if (shareInfo == null && nextType == 1) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "已经是最后一条了");
        }
        if (updateReadCount && shareInfo != null) {
            aCommonMapper.updateCount(Constants.TABLE_NAME_SHARE_INFO, 1, null, currentId);
            shareInfo.setReadCount((shareInfo.getReadCount() == null ? 0 : shareInfo.getReadCount()) + 1);
        }
        return shareInfo;
    }
}

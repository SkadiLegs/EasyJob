package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.enums.PostStatusEnum;
import com.neo.common.entity.query.ShareInfoQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.service.ShareInfoService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/11
 * @ClassName shareComtroller
 * @MethodName
 * @Params
 */

@RestController
@RequestMapping("/shareInfo")
public class shareController {

    @Autowired
    ShareInfoService shareInfoService;

    @PostMapping("/loadDataList")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_LIST)
    public R loadDataList(ShareInfoQuery query) {
        query.setOrderBy("desc");
        query.setQueryTextContent(true);
        PaginationResultVO paginationResultVO = shareInfoService.selectPage(query);
        return R.ok().data(paginationResultVO);
    }

    //TODO 优化: 修改效率低藕合度高
    @PostMapping("/postShare")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_POST)
    public R postShare(@VerifyParam(required = true) String shareIds) {
        ShareInfoQuery shareInfoQuery = new ShareInfoQuery();
        shareInfoQuery.setStatus(PostStatusEnum.POST.getStatus());
        shareInfoService.updateStatus(shareInfoQuery, shareIds);
        return R.ok();
    }

    //TODO 优化: 修改效率低藕合度高
    @PostMapping("/cancelPostShare")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_POST)
    public R cancelPostShare(@VerifyParam(required = true) String shareIds) {
        ShareInfoQuery shareInfoQuery = new ShareInfoQuery();
        shareInfoQuery.setStatus(PostStatusEnum.NO_POST.getStatus());
        shareInfoService.updateStatus(shareInfoQuery, shareIds);
        return R.ok();
    }


    @PostMapping("/saveShareInfo")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_EDIT)
    public R saveShareInfo(HttpSession session, Integer shareId,
                           @VerifyParam(required = true) String title,
                           @VerifyParam(required = true) Integer coverType,
                           String coverPath,
                           @VerifyParam(required = true) String content) {

        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        ShareInfoQuery shareInfoQuery = new ShareInfoQuery();
        shareInfoQuery.setShareId(shareId);
        shareInfoQuery.setTitle(title);
        shareInfoQuery.setCoverType(coverType);
        shareInfoQuery.setCoverPath(coverPath);
        shareInfoQuery.setContent(content);
        shareInfoQuery.setCreateUserId(String.valueOf(sessionUserAdminDto.getUserId()));
        shareInfoQuery.setCreateUserName(sessionUserAdminDto.getUserName());
        shareInfoService.saveShareInfo(shareInfoQuery, sessionUserAdminDto.getSuperAdmin());
        return R.ok();
    }

    @RequestMapping("/delShare")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_DEL)
    public R delShare(HttpSession session, @VerifyParam(required = true) String shareIds) {
        SessionUserAdminDto sessionAttribute = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        String[] splitIds = shareIds.split(",");
        shareInfoService.removeBatchShareInfo(splitIds, sessionAttribute.getSuperAdmin() ? null : sessionAttribute.getUserId());
        return R.ok();
    }

    @RequestMapping("/delShareBatch")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.SHARE_DEL_BATCH)
    public R delShareBatch(HttpSession session, @VerifyParam(required = true) String shareIds) {
        SessionUserAdminDto sessionAttribute = (SessionUserAdminDto) session.getAttribute(Constants.SESSION_KEY);
        String[] splitIds = shareIds.split(",");
        shareInfoService.removeBatchShareInfo(splitIds, sessionAttribute.getSuperAdmin() ? null : sessionAttribute.getUserId());
        return R.ok();
    }


}

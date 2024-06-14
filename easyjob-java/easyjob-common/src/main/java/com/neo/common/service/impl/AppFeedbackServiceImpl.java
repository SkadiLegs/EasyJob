package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.PageSize;
import com.neo.common.entity.enums.PostStatusEnum;
import com.neo.common.entity.po.AppFeedback;
import com.neo.common.entity.query.AppFeedbackQuery;
import com.neo.common.entity.vo.PaginationResultVO;
import com.neo.common.mapper.AppFeedbackMapper;
import com.neo.common.service.AppFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName AppFeedbackServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class AppFeedbackServiceImpl extends ServiceImpl<AppFeedbackMapper, AppFeedback> implements AppFeedbackService {

    @Resource
    AppFeedbackMapper appFeedbackMapper;

    @Override
    public PaginationResultVO<AppFeedback> findListByPage(AppFeedbackQuery query) {
        Page<AppFeedback> page = new Page<>(query.getPageNo() == null ? 0 : query.getPageNo(), query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize());
        QueryWrapper<AppFeedback> queryWrapper = new QueryWrapper<>();
        if (query.getStatus() != null) {
            queryWrapper.eq("status", query.getStatus());
        }
        if (query.getCreateTimeStart() != null && query.getCreateTimeEnd() != null) {
            queryWrapper.between("create_time", query.getCreateTimeStart(), query.getCreateTimeEnd());
        }
        if (query.getNickNameFuzzy() != null) {
            queryWrapper.like("nick_name", query.getNickNameFuzzy());
        }
        queryWrapper.orderByDesc(query.getOrderByDesc());

        Page<AppFeedback> appFeedbackPage = appFeedbackMapper.selectPage(page, queryWrapper);
        List<AppFeedback> collect = appFeedbackPage.getRecords().stream().filter(item -> !item.getStatus().equals(PostStatusEnum.POST.getStatus())).collect(Collectors.toList());
        PaginationResultVO<AppFeedback> paginationResultVO = new PaginationResultVO<>(
                (int) appFeedbackPage.getTotal(),
                (int) appFeedbackPage.getSize(),
                (int) appFeedbackPage.getCurrent(),
                (int) appFeedbackPage.getPages(),
                collect);
        return paginationResultVO;
    }
}

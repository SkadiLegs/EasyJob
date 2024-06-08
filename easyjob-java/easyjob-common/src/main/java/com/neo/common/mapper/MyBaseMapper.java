package com.neo.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/7
 * @ClassName MyBaseMapper
 * @MethodName
 * @Params
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    Integer insertBatchSomeColumn(List<T> entityList);
}

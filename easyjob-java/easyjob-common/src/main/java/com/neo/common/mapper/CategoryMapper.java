package com.neo.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neo.common.entity.po.Category;
import io.lettuce.core.dynamic.annotation.Param;

public interface CategoryMapper extends BaseMapper<Category> {

    void updateCategoryName(@Param("categoryId") Integer categoryId, @Param("categoryName") String categoryName);
}

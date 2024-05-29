package com.neo.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neo.common.entity.po.Category;
import com.neo.common.entity.query.CategoryQuery;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName CategoryService
 * @MethodName
 * @Params
 */
public interface CategoryService extends IService<Category> {
    List<Category> selectList(CategoryQuery query);

    void saveCategory(Category category);

}

package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.po.Category;
import com.neo.common.entity.query.CategoryQuery;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.mapper.CategoryMapper;
import com.neo.common.service.CategoryService;
import com.neo.common.uilts.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName CategoryServiceImpl
 * @MethodName
 * @Params
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public List<Category> selectList(CategoryQuery query) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc(query.getOrderBy());
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(Category category) {
        /**
         * @Description 如果query中的name不为空, 且查询数据库的数据大于1说明没有重复name则可以进行新增or更新
         * @Author Lenove
         * @Date 2024/5/29
         * [com.neo.common.entity.query.CategoryQuery]
         * void
         */
        if (category.getCategoryName() != null && Math.toIntExact(categoryMapper
                .selectCount(new QueryWrapper<Category>()
                        .eq("category_name", category.getCategoryName()))) > 1) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "分类名称重复");
        }
        Category categoryDB = new Category();
        category.setCategoryName(category.getCategoryName());
        category.setType(category.getType());
        if (category.getBgColor() != null) {
            category.setBgColor(category.getBgColor());
        } else {
            category.setIconPath(category.getIconPath());
        }

        if (category.getCategoryId() == null) {
            category.setSort(Math.toIntExact(categoryMapper.selectCount(new QueryWrapper<>())));
            categoryMapper.insert(category);
        } else {
            category.setCategoryId(category.getCategoryId());
            categoryMapper.updateById(category);
        }
        Category categoryInfo = categoryMapper.selectById(category.getCategoryId());
        /**
         * @Description 如果category与原来不同, 则修改表exam_question和表question_info中的name数据
         * @Author Lenove
         * @Date 2024/5/29
         * [com.neo.common.entity.query.CategoryQuery]
         * void
         */
        if (!categoryInfo.getCategoryName().equals(category.getCategoryName())) {
            categoryMapper.updateCategoryName(category.getCategoryId(), category.getCategoryName());
        }
    }
}

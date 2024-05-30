package com.neo.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.common.entity.enums.CategoryTypeEnum;
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
         * @Description 如果category_name与原来不同, 则修改表exam_question和表question_info中的name数据
         * @Author Lenove
         * @Date 2024/5/29
         * [com.neo.common.entity.query.CategoryQuery]
         * void
         */
        if (!categoryInfo.getCategoryName().equals(category.getCategoryName())) {
            categoryMapper.updateCategoryName(category.getCategoryId(), category.getCategoryName());
        }
    }

    /**
     * @Description 获取所有category的id, 它们顺序就是排序完毕的字符串, 根据这个顺序设置sort
     * @Author Lenove
     * @Date 2024/5/30
     * @MethodName changeSortById
     * @Param categoryIds:以逗号分割所有category的id字符串
     * @Return: null
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeSortById(String categoryIds) {
        String[] categoryIdArray = categoryIds.split(",");
        Integer index = 1;
        for (String categoryIdStr : categoryIdArray) {
            Integer cateoryId = Integer.parseInt(categoryIdStr);
            Category category = new Category();
            category.setSort(index);
            category.setCategoryId(cateoryId);
            categoryMapper.updateById(category);
            index++;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delAboutCategoryAll(Integer categoryId) {
        Integer delCount = categoryMapper.delAboutCategoryAll(categoryId);

        return delCount;
    }

    @Override
    public List<Category> categorySelectType(Integer type) {
        CategoryTypeEnum typeEnum = CategoryTypeEnum.getByType(type);
        if (typeEnum == null) {
            throw new EasyJobException(ResultCode.NOT_FOUND, "找不到该分类所属的类型");
        }
        QueryWrapper<Category> queryWrapper = new QueryWrapper();
        if (type != null && !type.equals(0)) {
            queryWrapper.eq("type", type);
        }
        queryWrapper.orderByAsc("sort");
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        return categories;
    }

}

package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.entity.po.Category;
import com.neo.common.entity.query.CategoryQuery;
import com.neo.common.service.CategoryService;
import com.neo.common.uilts.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/29
 * @ClassName categoryController
 * @MethodName
 * @Params
 */
@RestController("categoryController")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/loadAllCategory")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEOGRY_LIST)
    public R loadAllCategory(CategoryQuery query) {
        query.setOrderBy("sort");
        List<Category> categories = categoryService.selectList(query);
        return R.ok().data(categories);
    }

    @PostMapping("/saveCategory")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEOGRY_EDIT)
    public R saveCategory(Category category) {
        categoryService.saveCategory(category);
        return R.ok();
    }

    @PostMapping("/delCategory")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEOGRY_DEL)
    public R delCategory(@VerifyParam(required = true) Integer categoryId) {
        boolean removeById = categoryService.removeById(categoryId);
        return R.ok();
    }

    /**
     * @Description 删除所有表中与categoryId相关的数据
     * @Author Lenove
     * @Date 2024/5/30
     * @MethodName delAbout_Category_All
     * @Param categoryId
     * @Return: int 在category表中执行成功删除的category_id个数
     */
    @PostMapping("/delAboutCategoryAll223")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEOGRY_DEL)
    public R delAbout_Category_All(@VerifyParam(required = true) Integer categoryId) {
        Integer delCategoryAll = categoryService.delAboutCategoryAll(categoryId);
        return R.ok().data(delCategoryAll);
    }

    @PostMapping("/changeSort")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEOGRY_EDIT)
    public R changeSort(@VerifyParam(required = true) String categoryIds) {
        categoryService.changeSortById(categoryIds);
        return R.ok();
    }

    @PostMapping("/loadAllCategory4Select")
    @GlobalInterceptor(permissionCode = PermissionCodeEnum.CATEOGRY_LIST)
    public R loadAllCategory4Select(@VerifyParam(required = true) Integer type) {
        List<Category> categories = categoryService.categorySelectType(type);
        return R.ok().data(categories);
    }

}

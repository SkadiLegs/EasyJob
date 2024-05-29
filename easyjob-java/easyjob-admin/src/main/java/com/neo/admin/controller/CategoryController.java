package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
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
}

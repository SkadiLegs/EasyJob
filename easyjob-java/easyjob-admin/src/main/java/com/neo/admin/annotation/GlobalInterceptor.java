package com.neo.admin.annotation;


import com.neo.common.entity.enums.PermissionCodeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//作用在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalInterceptor {

    boolean checkParams() default true;

    boolean checkLogin() default true;

    // 不校验权限
    PermissionCodeEnum permissionCode() default PermissionCodeEnum.NO_PERMISSION;


}

package com.neo.common.annotation;

import com.neo.common.entity.enums.VerifyRegexEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)//@Retention是用来修饰注解的，注解的注解，也称为元注解
public @interface VerifyParam {
    /**
     * 正则校验
     *
     * @return 默认不校验
     */
    VerifyRegexEnum regex() default VerifyRegexEnum.NO;

    /**
     * 最小长度
     *
     * @Retrun
     */
    int min() default -1;

    /**
     * 最大长度
     *
     * @Return
     */

    int max() default -1;

    /**
     * 必填
     *
     * @return
     */
    boolean required() default false;
}

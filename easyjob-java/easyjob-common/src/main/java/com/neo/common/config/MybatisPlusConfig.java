package com.neo.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @Description 分页插件
 * @Author Lenove
 * @Date 2024/5/15
 */
@Configuration
@MapperScan("com.neo.common.mapper")
public class MybatisPlusConfig extends DefaultSqlInjector {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new
                PaginationInnerInterceptor(DbType.MYSQL));//由于各个数据库的语法会有差别，因此，要指明数据库类型
        return interceptor;
    }

    @Primary
    @Bean
    public MySqlInjector sqlInjector() {
        return new MySqlInjector();
    }


}

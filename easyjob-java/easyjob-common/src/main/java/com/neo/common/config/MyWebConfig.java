package com.neo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/14
 * @ClassName MyWebConfig
 * @MethodName
 * @Params
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Value("${project.folder}")
    private String realPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/getImage/**").addResourceLocations("file:/" + realPath);

    }
}

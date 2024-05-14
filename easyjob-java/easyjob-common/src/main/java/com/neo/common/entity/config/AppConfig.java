package com.neo.common.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 配置文件
 * @Author 程序员老罗
 * @Date 2023/9/17 16:03
 * @ClassName
 * @MethodName
 * @Params
 */
@Configuration
public class AppConfig {
    @Value("${project.folder:}")
    private String projectFolder;

    @Value("${super.admin.phones:}")
    private String superAdminPhones;


    @Value("${jwt.common.secret:}")
    private String jwtCommonSecret;

    @Value("${app.name:EasyJob}")
    private String appName;

    @Value("${app.domain:}")
    private String appDomain;


    public String getJwtCommonSecret() {
        return jwtCommonSecret;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public String getSuperAdminPhones() {
        return superAdminPhones;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppDomain() {
        return appDomain;
    }
}

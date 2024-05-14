package com.neo.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.neo"})
@MapperScan("com.neo.common.mapper")
@EnableAsync
@EnableScheduling
@EnableTransactionManagement

public class EasyjobAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyjobAdminApplication.class, args);
    }
}

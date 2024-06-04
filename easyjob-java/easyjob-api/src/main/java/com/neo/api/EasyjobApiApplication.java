package com.neo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.neo.api")
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class EasyjobApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyjobApiApplication.class, args);
    }
}
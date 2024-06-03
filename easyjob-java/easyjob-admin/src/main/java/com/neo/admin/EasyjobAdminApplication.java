package com.neo.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.neo.**"})
@MapperScan(basePackages = {"com.neo.**.mapper"})
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 2400, redisNamespace = "easyjob:session")
public class EasyjobAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyjobAdminApplication.class, args);
    }
}

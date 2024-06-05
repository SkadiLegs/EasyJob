package com.neo.admin.controller;

import com.neo.common.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    RedisTemplate redisTemplate;
    public static final Integer DEFAULT_ROOT_MENU_ID = 0;
    private static final String ROOT_MENU_NAME = "所有菜单";


    @GetMapping("test")
    public void TestGETContorller(Integer id) {

        redisTemplate.opsForValue().set("testEJKey", "value");
    }
}

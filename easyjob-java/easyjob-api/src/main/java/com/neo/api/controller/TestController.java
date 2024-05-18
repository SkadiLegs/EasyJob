package com.neo.api.controller;

import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.dto.CreateImageCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class TestController {

    @GetMapping("test")
    public String TestGETContorller() {
        return "hi TEST";
    }

    /*
    前端接口测试,在不放这
     */
    @GetMapping("/checkCode")
    public void checkCode(HttpServletResponse response, HttpSession session) throws IOException {
        CreateImageCode createImageCode = new CreateImageCode(130, 38, 5, 10);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = createImageCode.getCode();
        System.out.println("--///////" + code + "\\\\\\\\\\--");
        session.setAttribute(Constants.CHECK_CODE_KEY, code);
        createImageCode.write(response.getOutputStream());
    }
}

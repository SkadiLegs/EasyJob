package com.neo.admin.controller;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.dto.CreateImageCode;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.service.SysAccountService;
import com.neo.common.uilts.R;
import com.neo.common.uilts.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LoginController {

    @Autowired
    SysAccountService sysAccountService;

    //TODO 根据图片生成验证码
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

    @GetMapping("/login")
    @GlobalInterceptor
    //使用AOP切面+annotation实现参数校验 作用:解耦
    public R Login(HttpSession session,
                   @VerifyParam(required = true) String phone,
                   @VerifyParam(required = true) String password,
                   @VerifyParam(required = true) String checkCode) {

        if (!session.getAttribute(Constants.CHECK_CODE_KEY).equals(checkCode)) {
            throw new EasyJobException(ResultCode.ERROR_OTHER, "验证码错误");
        }

        SessionUserAdminDto login = sysAccountService.login(phone, password);
        session.setAttribute(Constants.SESSION_KEY, login);
        return R.ok();
    }

}

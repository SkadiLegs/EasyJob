package com.neo.common.exceptionhandler;


import com.neo.common.uilts.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //指定出现什么异常执行这个方法
    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error_other().message("执行了全局异常处理..");
    }
    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error_other().message("执行了ArithmeticException异常处理..");
    }
    //自定义异常处理
    @ExceptionHandler(EasyJobException.class)
    @ResponseBody//返回数据
    public R error(EasyJobException e){
        log.error(e.getMsg());
        e.printStackTrace();
        return R.error_other().code(e.getCode()).message(e.getMsg());
    }
}

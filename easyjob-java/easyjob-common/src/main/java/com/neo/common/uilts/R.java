package com.neo.common.uilts;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
//统一返回结果的类
//在类中，每个方法都返回该对象，是为了实现链式编程
@Data
public class R {
//    @ApiModelProperty(value = "是否成功")
    private Boolean success;

//    @ApiModelProperty(value = "返回码")
    private Integer code;

//    @ApiModelProperty(value = "返回消息")
    private String message;

//    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private R(){}//让无参构造不能被随意使用
    //成功的静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
    //失败的静态方法
    public static R error_other(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR_OTHER);
        r.setMessage("失败");
        return r;
    }
    ///找不到地址
    public static R error_not_found(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.NOT_FOUND);
        r.setMessage("地址不存在");
        return r;
    }
    //绕过前端的参数访问
    public static R error_600(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR_600);
        r.setMessage("非法参数访问");
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}

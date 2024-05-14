package com.neo.common.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//自定义异常构造类
@Data
@AllArgsConstructor//生成无参构造方法
@NoArgsConstructor//生成有参构造方法
public class EasyJobException extends RuntimeException {
    private Integer Code;//状态码
    private String msg;//异常信息

}

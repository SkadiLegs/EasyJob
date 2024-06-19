package com.neo.common.uilts;

public interface ResultCode {
    public static Integer SUCCESS = 20000;//成功
    public static Integer ERROR_OTHER = 20001;//其他失败
    public static Integer NOT_FOUND = 20002;//404
    public static Integer ERROR_600 = 20003;//数据(类型/格式)错误
    public static Integer ERROR_NAN = 20004;//空指针
    public static Integer ERROR_OUTTIME = 20005;//超时
    public static Integer ERROR_LOGIN_OUT_TIME = 901;//超时
    static Integer ERROR_NOPERMISSION = 902;//权限不足
    public static Integer ERROR_BAD = 20006;//创建文件失败
    static Integer CODE_601 = 60001;
    static Integer VERSION_ERROR = 92000; //版本错误
    static Integer APPUPDATE_BAD = 91000; //app更新失败
}

package com.neo.common.uilts;

public interface ResultCode {
    public static Integer SUCCESS = 20000;//成功
    public static Integer ERROR_OTHER = 20001;//其他失败
    public static Integer NOT_FOUND = 20002;//404
    public static Integer ERROR_600 = 20003;//网络异常
    public static Integer ERROR_NAN = 20004;//空指针
    public static Integer ERROR_OUTTIME = 20005;//超时
    public static Integer ERROR_LOGIN_OUT_TIME = 901;//超时
    public static Integer ERROR_NOPERMISSION = 902;//权限不足
    public static Integer ERROR_BAD = 20006;//创建文件失败
}

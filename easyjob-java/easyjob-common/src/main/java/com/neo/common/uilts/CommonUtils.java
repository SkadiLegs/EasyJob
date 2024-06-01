package com.neo.common.uilts;

import com.neo.common.exceptionhandler.EasyJobException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/6/1
 * @ClassName CommonUtils
 * @MethodName
 * @Params
 */
public class CommonUtils {
    /**
     * 检查参数是否为空
     *
     * @param param 要检查的参数对象
     * @throws EasyJobException 如果参数为空，则抛出业务异常
     */
    public static void checkParam(Object param) {
        try {
            // 获取参数对象的所有声明字段
            Field[] fields = param.getClass().getDeclaredFields();
            boolean notEmpty = false;

            // 遍历字段
            for (Field field : fields) {
                // 构建获取字段值的方法名
                String methodName = "get" + CommonUtils.upperCaseFirstLetter(field.getName());
                Method method = param.getClass().getMethod(methodName);

                // 调用方法获取字段值
                Object object = method.invoke(param);

                // 如果字段值不为空（字符串不为空或不是字符串类型）
                if (object != null && (object instanceof String && !StringUtils.isEmpty(object.toString())
                        || object != null && !(object instanceof String))) {
                    notEmpty = true;
                    break;
                }
            }

            // 如果所有字段值都为空，则抛出业务异常
            if (!notEmpty) {
                throw new EasyJobException(ResultCode.NOT_FOUND, "多参数更新，删除，必须有非空条件");
            }
        } catch (EasyJobException e) {
            // 如果捕获到业务异常，则直接抛出
            throw e;
        } catch (Exception e) {
            // 打印其他异常信息
            e.printStackTrace();
            // 抛出业务异常
            throw new EasyJobException(ResultCode.NOT_FOUND, "校验参数是否为空失败");
        }
    }

    public static String upperCaseFirstLetter(String field) {
        if (isEmpty(field)) {
            return field;
        }
        //如果第二个字母是大写，第一个字母不大写
        if (field.length() > 1 && Character.isUpperCase(field.charAt(1))) {
            return field;
        }
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }

    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || "null".equals(str) || "\u0000".equals(str)) {
            return true;
        } else if ("".equals(str.trim())) {
            return true;
        }
        return false;
    }

}

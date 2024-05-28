package com.neo.admin.aspect;

import com.neo.admin.annotation.GlobalInterceptor;
import com.neo.common.annotation.VerifyParam;
import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.dto.SessionUserAdminDto;
import com.neo.common.entity.enums.PermissionCodeEnum;
import com.neo.common.exceptionhandler.EasyJobException;
import com.neo.common.uilts.ResultCode;
import com.neo.common.uilts.VerifyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component("operationAspect")
public class OperationAspect {

    private static final String[] BASE_TYPE_ARRAY = new String[]{"java.lang.String", "java.lang.Integer", "java.lang.Long"};

    private final Logger logger = LoggerFactory.getLogger(OperationAspect.class);

    /*
    两种切面的写法
    1.先定义切面,在写切面位置的方法
    2.直接写切面的方法,再在其方法上注解切面位置
     */
    //方法1
    @Pointcut("@annotation(com.neo.admin.annotation.GlobalInterceptor)")
    private void pointcut() {
    }

    @Before("pointcut()")
    public void interceptorDo(JoinPoint point) {
        logger.info(Arrays.toString(point.getArgs()));
        // JoinPoint.getArgs():获取带参方法的 参数值 ,重点!是参数值
        Object[] arguments = point.getArgs();
        // 通过反射拿到方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        logger.info("方法名", method.getName());
        System.out.println("=====" + method.getName());
        //通过反射获取 @GlobalInterceptor 注解
        GlobalInterceptor globalInterceptor = method.getAnnotation(GlobalInterceptor.class);
        if (globalInterceptor == null) {
            return;
        }

        if (globalInterceptor.checkParams()) {
            validateParams(method, arguments);
        }
        /**
         * 校验登陆
         */
        if (globalInterceptor.checkLogin()) {
            checkLogin();
        }
        /**
         * 校验权限
         */
        if (globalInterceptor.permissionCode() != null && globalInterceptor.permissionCode() != PermissionCodeEnum.NO_PERMISSION) {
            checkPermission(globalInterceptor.permissionCode());
        }
    }

    //方法2
    @Before("@annotation(com.neo.admin.annotation.GlobalInterceptor)")
    public void interceptorDo2(JoinPoint point) {
        logger.info(point.getArgs().toString());
    }


    private void validateParams(Method method, Object[] arguments) {
        /* Parameter类位于 java.lang.reflect 包中
           主要用于在程序运行状态中，动态地获取参数信息
           此处获取参数
         */
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object value = arguments[i];
            //获取每一个参数上面的@VerifyParam注解
            VerifyParam verifyParam = parameter.getAnnotation(VerifyParam.class);
            if (verifyParam == null) {
                continue;
            }
            //判断数据类型
            String paramTypeName = parameter.getParameterizedType().getTypeName();
            // 传入的paramTypeName是 类型(BASE_TYPE_ARRAY)中 的某一个否?
            if (ArrayUtils.contains(BASE_TYPE_ARRAY, paramTypeName)) {
                //是类型(BASE_TYPE_ARRAY)中 的某一个
                logger.info("BastType");
                /**
                 * 传入 判断的值 和 verifyParam注解
                 */
                checkValue(value, verifyParam);
            } else {
                //不在 类型(BASE_TYPE_ARRAY) 中
                checkObjValue(parameter, value);
                logger.info("OtherObject");
            }
        }
    }

    /**
     * @param parameter 参数
     * @param value     参数值
     */

    private void checkObjValue(Parameter parameter, Object value) {
        //TODO
        try {
            //获取参数类型
            String typeName = parameter.getParameterizedType().getTypeName();
            Class<?> aClass = Class.forName(typeName);
            /**
             * Field对象反映此 Class 对象所表示的类或接口的指定已声明字段
             * getDeclaredFields()和getFields()的区别:
             * getFields()获取到当前类、父类或父类接口的所有 public 修饰的字段
             * getDeclaredFields()获取当前类的所有字段包括public、private、protected等所有，但不包括父类public修饰的字段
             */

            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                //是否有@VerifyParam注解
                VerifyParam fieldVerifyParam = field.getAnnotation(VerifyParam.class);
                if (fieldVerifyParam == null) {
                    continue;
                }
                field.setAccessible(true);
                Object resultValue = field.get(value);
                checkValue(resultValue, fieldVerifyParam);
            }
        } catch (Exception e) {
            logger.error("参数校验失败", e);
            throw new EasyJobException(ResultCode.ERROR_600, "参数校验失败");
        }
    }


    /**
     * @param value
     * @param verifyParam 空值校验,长度校验,正则校验
     */
    private void checkValue(Object value, VerifyParam verifyParam) {
        //是否为空
        Boolean isEmpty = value == null || StringUtils.isEmpty(value.toString());
        //取得长度
        Integer length = value == null ? 0 : value.toString().length();
        /**
         * 校验是否为空
         */
        if (isEmpty && verifyParam.required()) {
            throw new EasyJobException(ResultCode.ERROR_600, "非法参数,参数为空");
        }

        /**
         * 校验长度
         */
        if (!isEmpty
                && ((verifyParam.max() != -1)
                && verifyParam.max() < length
                || (verifyParam.min() != -1)
                && verifyParam.min() > length)) {
            throw new EasyJobException(ResultCode.ERROR_600, "非法参数,长度错误");
        }
        /**
         * 校验正则
         */
        if (!isEmpty && !StringUtils.isEmpty(verifyParam.regex().getRegex())
                && !VerifyUtils.verify(verifyParam.regex(), String.valueOf(value))) {
            throw new EasyJobException(ResultCode.ERROR_600, "非法参数");
        }


    }

    /**
     * @Description 登陆校验
     * @Author Lenove
     * @Date 2024/5/27
     * @MethodName checkLogin
     * @Param null
     * @Return: null
     */
    void checkLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) request.getSession().getAttribute(Constants.SESSION_KEY);
        if (sessionUserAdminDto == null) {
            throw new EasyJobException(ResultCode.ERROR_LOGIN_OUT_TIME, "登陆超时");
        }
    }

    /**
     * @throws EasyJobException 如果用户没有权限，则抛出业务异常
     * @Description 权限参数校验
     * @Author Lenove
     * @Date 2024/5/27
     * @MethodName checkPermission
     * @Param permissionCodeEnum 权限代码枚举
     */
    void checkPermission(PermissionCodeEnum permissionCodeEnum) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SessionUserAdminDto sessionUserAdminDto = (SessionUserAdminDto) request.getSession().getAttribute(Constants.SESSION_KEY);
        // 拿到所有权限编码
        List<String> permissionCodeList = sessionUserAdminDto.getPermissionCodeList();
        if (!permissionCodeList.contains(permissionCodeEnum.getCode())) {
            throw new EasyJobException(ResultCode.ERROR_NOPERMISSION, "权限不足");
        }
    }
}

package com.ysytech.tourism.api.config.security;

import com.ysytech.tourism.api.annotation.Login;
import com.ysytech.tourism.common.call.ResultCode;
import com.ysytech.tourism.common.exception.CustomException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author mc
 * Create date 2019/12/16 15:05
 * Version 1.0
 * Description
 */

@Aspect
@Component
public class AuthorizationAop {

    @Resource
    private InterfaceAuth interfaceAuth;

    @Pointcut("@annotation(com.ysytech.tourism.api.annotation.Login)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        Login annotation = method.getAnnotation(Login.class);
        if (annotation.required()){
            ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                if (!interfaceAuth.hasPermission(request)) {
                    throw new CustomException(ResultCode.PERMISSION_NO_ACCESS.getCode(), ResultCode.PERMISSION_NO_ACCESS.getMsg());
                }
            }else {
                throw new RuntimeException("接口设置错误");
            }
        }
        return joinPoint.proceed();
    }
}

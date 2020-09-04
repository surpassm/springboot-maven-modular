package com.ysytech.tourism.api.config.security;

import com.ysytech.tourism.common.annotation.JwtConstants;
import com.ysytech.tourism.api.annotation.Login;
import com.ysytech.tourism.common.call.ResultCode;
import com.ysytech.tourism.common.exception.CustomException;
import com.ysytech.tourism.common.util.JwtUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author mc
 * Create date 2019/3/4 15:53
 * Version 1.0
 * Description
 */
@Component
public class TokenMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Long.class) && methodParameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = nativeWebRequest.getHeader(JwtConstants.AUTHORIZATION_HEADER_KEY);
        if (token != null) {
            return Long.parseLong(JwtUtils.getToken(token));
        }
		throw new CustomException(ResultCode.PERMISSION_NO_ACCESS.getCode(),"请携带header:Authorization");

    }
}

package com.ysytech.tourism.api.util;

import com.ysytech.tourism.common.call.ResultCode;
import com.ysytech.tourism.common.exception.CustomException;
import org.springframework.validation.BindingResult;

/**
 * @author mc
 * Create date 2020/9/3 17:36
 * Version 1.0
 * Description
 */
public class Binding {

    public static void check(BindingResult errors){
        if (errors.hasErrors()){
            StringBuilder builder = new StringBuilder();
            errors.getAllErrors().forEach(i -> builder.append(i.getDefaultMessage()).append(","));
            throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(),builder.toString());
        }
    }
}

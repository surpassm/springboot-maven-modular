package com.ysytech.tourism.api.config.security.impl;

import com.ysytech.tourism.common.annotation.JwtConstants;
import com.ysytech.tourism.api.config.security.InterfaceAuth;
import com.ysytech.tourism.common.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mc
 * Create date 2019/12/16 13:08
 * Version 1.0
 * Description
 */
@Slf4j
@Component("interfaceAuth")
public class InterfaceAuthImpl implements InterfaceAuth {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request) {
        String token = (String) request.getAttribute(JwtConstants.AUTHORIZATION_HEADER_KEY);
        if (token != null) {
            String userId = JwtUtils.getToken(token);
//        boolean flag = false;
//        String requestURI = request.getRequestURI();
//        if (!requestURI.equals("/favicon.ico")) {
//            log.info("请求验证的URL" + requestURI);
//            for (UserMenu menu : menuList) {
//                if (menu.getMenuUrl() != null && antPathMatcher.match(menu.getMenuUrl(), requestURI)) {
//                    flag = true;
//                    break;
//                }
//            }
//        }
        }
        return false;
    }
}

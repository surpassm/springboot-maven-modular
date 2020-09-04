package com.ysytech.tourism.api.config.web;

import com.ysytech.tourism.api.config.security.TokenInterceptor;
import com.ysytech.tourism.api.config.security.TokenMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @author mc
 * Create date 2019/3/4 15:53
 * Version 1.0
 * Description
 */

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    @Resource
    private TokenMethodArgumentResolver tokenMethodArgumentResolver;
    @Resource
	private TokenInterceptor tokenInterceptor;
	@Resource
	private List<String> noVerify;



    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenMethodArgumentResolver);
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");

		interceptorRegistration.excludePathPatterns(noVerify);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		try {
			String path  = new File("").getCanonicalPath();
			registry.addResourceHandler("/upload/**").addResourceLocations("file:///"+path+"/upload/");
			registry.addResourceHandler("/static/**").addResourceLocations("file:///"+path+"/static/");
		}catch (Exception e){
		}
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "OPTIONS", "PUT")
				.allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method","Access-Control-Request-Headers")
				.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
				.allowCredentials(true).maxAge(36000);
	}

}

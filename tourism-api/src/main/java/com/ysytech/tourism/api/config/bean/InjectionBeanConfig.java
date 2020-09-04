package com.ysytech.tourism.api.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mc
 * Create date 2019/7/17 16:23
 * Version 1.0
 * Description 注入Bean对象控制
 */
@Configuration
public class InjectionBeanConfig {

    /**
     * 不需要验证的URL
     *
     * @return
     */
    @Bean
    public List<String> noVerify() {
        List<String> result = new ArrayList<>();
		result.add("/swagger-ui**");
		result.add("/images/**");
		result.add("/webjars/**");
		result.add("/v2/api-docs**");
		result.add("/swagger-resources/**");
		result.add("/error**");
		result.add("/csrf**");
		result.add("/");
        return result;
    }

    /**
     * 密码加密对象
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 执行定时任务的线程池配置类
     * @return TaskScheduler
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 定时任务执行线程池核心线程数
        taskScheduler.setPoolSize(4);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setThreadNamePrefix("TaskSchedulerThreadPool-");
        return taskScheduler;
    }
}

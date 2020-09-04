package com.ysytech.tourism.api.config.runner;

import com.ysytech.tourism.api.config.task.CronTaskRegistrar;
import com.ysytech.tourism.api.config.task.SchedulingRunnable;
import com.ysytech.tourism.task.entity.SysTask;
import com.ysytech.tourism.task.service.SysTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mc
 * Create date 2019/9/6 16:34
 * Version 1.0
 * Description
 */
@Slf4j
@Configuration
public class ApplicationRunnerConfig implements ApplicationRunner {
    @Resource
    private SysTaskService sysTaskService;
    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //查询数据库中启动的定时任务列表
//		cronTaskRun();
    }



    /**
     * 项目启动就开始执行数据库启动的定时任务
     */
    void cronTaskRun() {
        List<SysTask> taskList = sysTaskService.select(SysTask.builder().jobStatus(1).build());
        if (!CollectionUtils.isEmpty(taskList)) {
            taskList.forEach(job ->
                    cronTaskRegistrar.addCronTask(new SchedulingRunnable(job.getBeanName(), job.getMethodName(),
                            job.getMethodParams()), job.getCronExpression())
            );
        }
    }

}

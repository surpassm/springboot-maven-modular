package com.ysytech.tourism.task.service;

import com.ysytech.tourism.task.entity.SysTask;

import java.util.List;

/**
 * @author mc
 * Create date 2020/9/3 13:53
 * Version 1.0
 * Description
 */
public interface SysTaskService {


    List<SysTask> select(SysTask sysTask);
}

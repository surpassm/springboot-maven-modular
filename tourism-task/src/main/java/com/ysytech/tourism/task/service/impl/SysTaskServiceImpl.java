package com.ysytech.tourism.task.service.impl;

import com.ysytech.tourism.task.entity.SysTask;
import com.ysytech.tourism.task.mapper.SysTaskMapper;
import com.ysytech.tourism.task.service.SysTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mc
 * Create date 2020/9/3 13:53
 * Version 1.0
 * Description
 */
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class SysTaskServiceImpl implements SysTaskService {


    @Resource
    private SysTaskMapper sysTaskMapper;


    @Override
    public List<SysTask> select(SysTask sysTask) {
        return sysTaskMapper.select(SysTask.builder().jobStatus(1).build());
    }
}

package com.ysytech.tourism.auth.mapper;

import com.ysytech.tourism.auth.entity.OperationsLog;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * LogMapper
 *
 * @author zhangquanli
 */
public interface OperationsLogMapper extends Mapper<OperationsLog> , MySqlMapper<OperationsLog> {
}

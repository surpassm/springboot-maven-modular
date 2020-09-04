package com.ysytech.tourism.auth.mapper;

import com.ysytech.tourism.auth.entity.RolePower;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限与角色关联表持久层
  */
public interface RolePowerMapper extends Mapper<RolePower>, MySqlMapper<RolePower> {


}

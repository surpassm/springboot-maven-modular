package com.ysytech.tourism.auth.mapper;

import com.ysytech.tourism.auth.entity.PowerMenu;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
  * @author mc
  * Create date 2020-02-10 10:15:19
  * Version 1.0
  * Description 权限与菜单关联表持久层
  */
public interface PowerMenuMapper extends Mapper<PowerMenu>, MySqlMapper<PowerMenu> {


}

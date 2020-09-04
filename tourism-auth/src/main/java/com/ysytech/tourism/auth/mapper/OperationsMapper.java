package com.ysytech.tourism.auth.mapper;

import com.ysytech.tourism.auth.domain.OperationsDTO;
import com.ysytech.tourism.auth.entity.Operations;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 后台功能接口持久层
  */
public interface OperationsMapper extends Mapper<Operations>, MySqlMapper<Operations> {


	List<OperationsDTO> findAllParent();

	List<OperationsDTO> findAllChild(@Param("parentId") Long parentId);

	List<Operations> findByUserId(@Param("userId") Long userId);
}

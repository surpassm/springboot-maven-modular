package com.ysytech.tourism.auth.mapper;

import com.ysytech.tourism.auth.domain.PositionsDTO;
import com.ysytech.tourism.auth.entity.Positions;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 职位持久层
  */
public interface PositionsMapper extends Mapper<Positions>, MySqlMapper<Positions> {


	List<PositionsDTO> findAllParent();

	List<PositionsDTO> findAllChild(@Param("parentId") Long parentId);
}

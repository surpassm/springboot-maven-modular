package com.ysytech.tourism.auth.mapper;

import com.ysytech.tourism.auth.domain.GroupsDTO;
import com.ysytech.tourism.auth.entity.Department;
import com.ysytech.tourism.auth.entity.Groups;
import com.ysytech.tourism.auth.entity.Menu;
import com.ysytech.tourism.auth.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 权限持久层
  */
public interface GroupsMapper extends Mapper<Groups>, MySqlMapper<Groups> {


	List<GroupsDTO> findAllParent();

	List<GroupsDTO> findAllChild(@Param("parentId") Long parentId);

	List<Department> findDepartmentByGroupId(@Param("groupId") Long groupId);

	List<Menu> findMenuByGroupId(@Param("groupId") Long groupId);

	List<Role> findRoleByGroupId(@Param("groupId") Long groupId);
}

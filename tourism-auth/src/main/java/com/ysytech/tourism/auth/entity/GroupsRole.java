package com.ysytech.tourism.auth.entity;

import lombok.*;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * @author mc
 * Create date 2019/3/14 18:09
 * Version 1.0
 * Description
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NameStyle(Style.camelhump)
@Table
@org.hibernate.annotations.Table(appliesTo = "groups_role", comment = "组角色")
public class GroupsRole implements Serializable {

	@Id
	@Min(0)
	@KeySql(useGeneratedKeys = true)
	@Column(columnDefinition="bigint COMMENT '系统标识'")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(targetEntity = Groups.class)
	@JoinColumn(name = "group_id", referencedColumnName = "id")
	private Long groupId;
	@ManyToOne(targetEntity = Role.class)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Long roleId;

	@Min(0)
	@Max(1)
	private Integer isDelete;
}

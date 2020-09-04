package com.ysytech.tourism.auth.service.impl;

import com.github.pagehelper.Page;
import com.ysytech.tourism.auth.domain.MenuDTO;
import com.ysytech.tourism.auth.domain.UserInfoVO;
import com.ysytech.tourism.auth.entity.*;
import com.ysytech.tourism.auth.mapper.UserGroupsMapper;
import com.ysytech.tourism.auth.mapper.UserInfoMapper;
import com.ysytech.tourism.auth.mapper.UserRoleMapper;
import com.ysytech.tourism.auth.service.MenuService;
import com.ysytech.tourism.auth.service.OperationsService;
import com.ysytech.tourism.auth.service.UserInfoService;
import com.ysytech.tourism.common.annotation.JwtConstants;
import com.ysytech.tourism.common.call.R;
import com.ysytech.tourism.common.call.ResultCode;
import com.ysytech.tourism.common.call.Token;
import com.ysytech.tourism.common.exception.CustomException;
import com.ysytech.tourism.common.util.JwtUtils;
import com.ysytech.tourism.common.util.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ysytech.tourism.common.call.R.ok;


/**
 * @author mc
 * Create date 2020-02-10 10:15:21
 * Version 1.0
 * Description 用户实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
public class UserInfoServiceImpl extends BaseServiceImpl implements UserInfoService {
	@Resource
	private UserInfoMapper userInfoMapper;
	@Resource
	private PasswordEncoder passwordEncoder;
	@Resource
	private UserGroupsMapper userGroupsMapper;
	@Resource
	private UserRoleMapper userRoleMapper;
	@Resource
	private MenuService menuService;
	@Resource
	private OperationsService operationsService;

	@Override
	public UserInfo insert(UserInfo userInfo) {
		userInfoMapper.insert(userInfo);
		return userInfo;
	}

	@Override
	public void update(UserInfo userInfo) {
		userInfoMapper.updateByPrimaryKeySelective(userInfo);
	}

	@Override
	public void deleteById(Long id) {
		Optional<UserInfo> byId = this.findById(id);
		if (!byId.isPresent()) {
			throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
		}
		UserInfo userInfo = byId.get();
		userInfo.setIsDelete(1);
		userInfo.setUpdateTime(LocalDateTime.now());
		this.update(userInfo);
	}


	@Override
	public Optional<UserInfo> findById(Long id) {
		return Optional.ofNullable(userInfoMapper.selectByPrimaryKey(id));

	}

	public Optional<UserInfo> findOne(UserInfo userInfo) {
		return Optional.ofNullable(userInfoMapper.selectOne(userInfo));
	}

	@Override
	public R pageQuery(Integer page, Integer size, String sort, UserInfoVO userInfoVO) {
		super.pageQuery(page, size, sort);
		Example.Builder builder = new Example.Builder(UserInfo.class);
		builder.where(WeekendSqls.<UserInfo>custom().andEqualTo(UserInfo::getIsDelete, 0));
		if (userInfoVO != null) {
		}
		Page<UserInfo> all = (Page<UserInfo>) userInfoMapper.selectByExample(builder.build());
		return ok(all.getTotal(), all.getResult());
	}


	@Override
	public UserInfo insertOrUpdate(UserInfoVO vo) {
		//验证id不能存在
		UserInfo userInfo = vo.convertTo();
		if (userInfo.getId() != null) {
			//效验密码是否为空
			if (StringUtils.isNotEmpty(userInfo.getPassword().trim())) {
				if (!ValidateUtil.isPassword(userInfo.getPassword())) {
					throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(), "规则：小写字母、大写字母、数字、特殊符号的两种及两种以上");
				}
				//加密设置
				userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			}
			this.update(userInfo);
			userInfo.setPassword(null);
		} else {
			//判断是否启用 为空默认启用
			if (userInfo.getIsEnable() == null) {
				userInfo.setIsDelete(1);
			}
			//密码效验
			if (StringUtils.isEmpty(userInfo.getPassword().trim())) {
				throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(), "密码不能为空");
			}
			if (!ValidateUtil.isPassword(userInfo.getPassword())) {
				throw new CustomException(ResultCode.PARAM_IS_INVALID.getCode(), "规则：小写字母、大写字母、数字、特殊符号的两种及两种以上");
			}
			//加密设置
			userInfo.setPassword(passwordEncoder.encode(vo.getPassword()));
			//初始化数据
			userInfo.setIsDelete(0);
			userInfo.setCreateTime(LocalDateTime.now());
			this.insert(userInfo);
			userInfo.setPassword(null);
		}

		return userInfo;
	}



	@Override
	public UserInfo createSupperAdmin() {
		String username = "admin";
		String password = "123456";
		UserInfo admin = userInfoMapper.selectOne(UserInfo.builder().isDelete(0).username(username).build());
		if (admin != null) {
			admin.setPassword(password);
			return admin;
		}
		String encode = passwordEncoder.encode(password);
		UserInfo build = UserInfo.builder()
				.username(username)
				.password(encode)
				.isDelete(0)
				.isEnable(1)
				.createTime(LocalDateTime.now())
				.build();
		userInfoMapper.insert(build);
		build.setPassword(password);
		return build;
	}

	/**
	 * 账户密码登录
	 *
	 * @param username 账号
	 * @param password 密码
	 * @return token
	 */
	@Override
	public Token login(String username, String password) {
		Optional<UserInfo> userInfoOptional = findOne(UserInfo.builder()
				.username(username)
				.isDelete(0)
				.isEnable(1)
				.build());
		if (!userInfoOptional.isPresent()) {
			throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
		}
		UserInfo userInfo = userInfoOptional.get();
		//密码效验
		if (!passwordEncoder.matches(password, userInfo.getPassword())) {
			log.error(ResultCode.USER_LOGIN_ERROR.getMsg());
			throw new CustomException(ResultCode.USER_LOGIN_ERROR.getCode(), ResultCode.USER_LOGIN_ERROR.getMsg());
		}
		//生成token
		String token = JwtUtils.getSub(userInfo.getId().toString());
		userInfo.setLandingTime(LocalDateTime.now());
		update(userInfo);
		return Token.builder().key(JwtConstants.AUTHORIZATION_HEADER_KEY).token(token).build();
	}

	/**
	 * 小程序登陆
	 *
	 * @param mobile 手机号码
	 * @return token
	 */
	@Override
	public Token smallLogin(String mobile) {
		Optional<UserInfo> one = findOne(UserInfo.builder().username(mobile).isDelete(0).build());
		if (!one.isPresent()) {
			throw new CustomException(ResultCode.USER_NOT_EXIST.getCode(), ResultCode.USER_NOT_EXIST.getMsg());
		}
		UserInfo userInfo = one.get();
		String token = JwtUtils.getSub(userInfo.getId().toString());
		return Token.builder().key(JwtConstants.AUTHORIZATION_HEADER_KEY).token(token).build();
	}

	@Override
	public void addUserGroup(Long userInfoId, Long groupId) {
		UserGroups build = UserGroups.builder().groupId(groupId).userId(userInfoId).build();
		if (userGroupsMapper.selectCount(build) == 0) {
			userGroupsMapper.insert(build);
		} else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(), ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}

	@Override
	public void deleteUserGroup(Long userInfoId, Long groupId) {
		userGroupsMapper.delete(UserGroups.builder().groupId(groupId).userId(userInfoId).build());
	}

	@Override
	public R pageQueryGroup(Integer page, Integer size, String sort, Long userInfoId) {
		super.pageQuery(page, size, sort);
		Page<Groups> all = (Page<Groups>) userInfoMapper.findGroupByUserId(userInfoId);
		return ok(all.getTotal(), all.getResult());
	}

	@Override
	public void addUserRole(Long userInfoId, Long roleId) {
		UserRole build = UserRole.builder().roleId(roleId).userId(userInfoId).build();
		if (userRoleMapper.selectCount(build) == 0) {
			userRoleMapper.insert(build);
		} else {
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(), ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
	}

	@Override
	public void deleteUserRole(Long userInfoId, Long roleId) {
		userRoleMapper.delete(UserRole.builder().roleId(roleId).userId(userInfoId).build());
	}

	@Override
	public R pageQueryRole(Integer page, Integer size, String sort, Long userInfoId) {
		super.pageQuery(page, size, sort);
		Page<Role> all = (Page<Role>) userInfoMapper.findRoleByUserId(userInfoId);
		return ok(all.getTotal(), all.getResult());
	}

	@Override
	public List<MenuDTO> selectUserMenu(Long userId) {
		return menuService.findByUserId(userId);
	}

	@Override
	public List<Operations> selectUserOperations(Long userId) {
		return operationsService.findByUserId(userId);
	}

	@Override
	public boolean getUsername(String username) {
		Optional<UserInfo> one = findOne(UserInfo.builder().username(username).isDelete(0).build());
		return one.isPresent();
	}
}


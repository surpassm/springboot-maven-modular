package com.ysytech.tourism.api.controller;

import com.ysytech.tourism.api.annotation.Login;
import com.ysytech.tourism.auth.entity.UserInfo;
import com.ysytech.tourism.auth.service.UserInfoService;
import com.ysytech.tourism.common.call.ResultCode;
import com.ysytech.tourism.common.exception.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author mc
 * Create date 2020/2/10 14:35
 * Version 1.0
 * Description
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/token/")
@Api(tags = "2、token_API")
public class TokenController {

	@Resource
	private UserInfoService userInfoService;

	@Login
	@PostMapping("v1/getToken")
	@ApiOperation(value = "根据token获取账户信息")
	public Object getUserInfo(@ApiParam(hidden = true) @Login Long userId) {
		Optional<UserInfo> optional = userInfoService.findById(userId);
		if (!optional.isPresent()){
			throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
		}
		return optional.get();
	}

	@Login
	@PostMapping("v1/selectUserId")
	@ApiOperation(value = "根据token获取账户主键")
	public Object selectUserId(@ApiParam(hidden = true) @Login Long userId) {
		Map<String,Long> result = new HashMap<>(1);
		result.put("id",userId);
		return result;
	}
}

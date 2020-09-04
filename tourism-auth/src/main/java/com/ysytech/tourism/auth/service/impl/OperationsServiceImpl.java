package com.ysytech.tourism.auth.service.impl;

import com.github.pagehelper.Page;
import com.ysytech.tourism.auth.domain.OperationsDTO;
import com.ysytech.tourism.auth.domain.OperationsVO;
import com.ysytech.tourism.auth.entity.Operations;
import com.ysytech.tourism.auth.mapper.OperationsMapper;
import com.ysytech.tourism.auth.service.OperationsService;
import com.ysytech.tourism.common.call.R;
import com.ysytech.tourism.common.call.ResultCode;
import com.ysytech.tourism.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
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
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 后台功能接口实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class OperationsServiceImpl extends BaseServiceImpl implements OperationsService {
    @Resource
    private OperationsMapper operationsMapper;

    @Override
    public Operations insert(Operations operations) {
        operationsMapper.insert(operations);
        return operations;
    }

    @Override
    public void update(Operations operations) {
        operationsMapper.updateByPrimaryKeySelective(operations);
    }

    @Override
    public void deleteById(Long id){
        Optional<Operations> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        Operations operations = byId.get();
        this.update(operations);
    }


    @Override
    public Optional<Operations> findById(Long id) {
        return Optional.ofNullable(operationsMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, OperationsVO operationsVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(Operations.class);
		builder.where(WeekendSqls.<Operations>custom().andEqualTo(Operations::getIsDelete,0));
        if(operationsVO != null){
        }
		builder.where(WeekendSqls.<Operations>custom().andIsNull(Operations::getParentId));
        Page<Operations> all = (Page<Operations>) operationsMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public Operations insertOrUpdate(OperationsVO vo) {
		Operations convert = vo.convertTo();

		//父级效验
		Long parentId = convert.getParentId();
		if (parentId != null){
			if (!findById(parentId).isPresent()){
				throw new CustomException(ResultCode.RESULT_DATA_NONE.getCode(),ResultCode.RESULT_DATA_NONE.getMsg());
			}
		}
		if (convert.getId() == null){
			convert.setIsDelete(0);
			convert.setCreateTime(LocalDateTime.now());
			this.insert(convert);
		}else {
			this.update(convert);
		}
		return convert;
    }


	@Override
	public List<OperationsDTO> findAllParent() {
		return operationsMapper.findAllParent();
	}

	@Override
	public List<OperationsDTO> findAllChild(Long parentId) {
		return operationsMapper.findAllChild(parentId);
	}

	@Override
	public List<Operations> findByUserId(Long userId) {
		return operationsMapper.findByUserId(userId);
	}
}


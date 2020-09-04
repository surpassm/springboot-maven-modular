package com.ysytech.tourism.auth.service.impl;

import com.github.pagehelper.Page;
import com.ysytech.tourism.auth.domain.RegionProvincesVO;
import com.ysytech.tourism.auth.entity.RegionProvinces;
import com.ysytech.tourism.auth.mapper.RegionProvincesMapper;
import com.ysytech.tourism.auth.service.RegionProvincesService;
import com.ysytech.tourism.common.call.R;
import com.ysytech.tourism.common.call.ResultCode;
import com.ysytech.tourism.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Optional;

import static com.ysytech.tourism.common.call.R.ok;


/**
  * @author mc
  * Create date 2020-02-10 10:15:20
  * Version 1.0
  * Description 省实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class RegionProvincesServiceImpl extends BaseServiceImpl implements RegionProvincesService {
    @Resource
    private RegionProvincesMapper regionProvincesMapper;

    @Override
    public RegionProvinces insert(RegionProvinces regionProvinces) {
        regionProvincesMapper.insert(regionProvinces);
        return regionProvinces;
    }

    @Override
    public void update(RegionProvinces regionProvinces) {
        regionProvincesMapper.updateByPrimaryKeySelective(regionProvinces);
    }

    @Override
    public void deleteById(Long id){
        Optional<RegionProvinces> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        RegionProvinces regionProvinces = byId.get();
        this.update(regionProvinces);
    }


    @Override
    public Optional<RegionProvinces> findById(Long id) {
        return Optional.ofNullable(regionProvincesMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, RegionProvincesVO regionProvincesVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(RegionProvinces.class);
        if(regionProvincesVO != null){
        }
        Page<RegionProvinces> all = (Page<RegionProvinces>) regionProvincesMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public RegionProvinces insertVO(RegionProvincesVO vo) {
        return null;
    }

    @Override
    public RegionProvinces updateVO(RegionProvincesVO vo) {
        return null;
    }
}


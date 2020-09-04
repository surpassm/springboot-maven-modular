package com.ysytech.tourism.auth.service.impl;

import com.github.pagehelper.Page;
import com.ysytech.tourism.auth.domain.RegionAreasVO;
import com.ysytech.tourism.auth.entity.RegionAreas;
import com.ysytech.tourism.auth.mapper.RegionAreasMapper;
import com.ysytech.tourism.auth.service.RegionAreasService;
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
  * Description 区县信息表实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class RegionAreasServiceImpl extends BaseServiceImpl implements RegionAreasService {
    @Resource
    private RegionAreasMapper regionAreasMapper;

    @Override
    public RegionAreas insert(RegionAreas regionAreas) {
        regionAreasMapper.insert(regionAreas);
        return regionAreas;
    }

    @Override
    public void update(RegionAreas regionAreas) {
        regionAreasMapper.updateByPrimaryKeySelective(regionAreas);
    }

    @Override
    public void deleteById(Long id){
        Optional<RegionAreas> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        RegionAreas regionAreas = byId.get();
        this.update(regionAreas);
    }


    @Override
    public Optional<RegionAreas> findById(Long id) {
        return Optional.ofNullable(regionAreasMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, RegionAreasVO regionAreasVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(RegionAreas.class);
        if(regionAreasVO != null){
        }
        Page<RegionAreas> all = (Page<RegionAreas>) regionAreasMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public RegionAreas insertVO(RegionAreasVO vo) {
        return null;
    }

    @Override
    public RegionAreas updateVO(RegionAreasVO vo) {
        return null;
    }
}


package com.ysytech.tourism.auth.service.impl;

import com.github.pagehelper.Page;
import com.ysytech.tourism.auth.domain.PageElementVO;
import com.ysytech.tourism.auth.entity.PageElement;
import com.ysytech.tourism.auth.mapper.PageElementMapper;
import com.ysytech.tourism.auth.service.PageElementService;
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
  * Description 页面元素实现类
  */
@Slf4j
@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class PageElementServiceImpl extends BaseServiceImpl implements PageElementService {
    @Resource
    private PageElementMapper pageElementMapper;

    @Override
    public PageElement insert(PageElement pageElement) {
        pageElementMapper.insert(pageElement);
        return pageElement;
    }

    @Override
    public void update(PageElement pageElement) {
        pageElementMapper.updateByPrimaryKeySelective(pageElement);
    }

    @Override
    public void deleteById(Long id){
        Optional<PageElement> byId = this.findById(id);
        if (!byId.isPresent()) {
            throw new CustomException(ResultCode.ERROR.getCode(), ResultCode.RESULT_DATA_NONE.getMsg());
        }
        PageElement pageElement = byId.get();
        this.update(pageElement);
    }


    @Override
    public Optional<PageElement> findById(Long id) {
        return Optional.ofNullable(pageElementMapper.selectByPrimaryKey(id));

    }

    @Override
    public R pageQuery(Integer page, Integer size, String sort, PageElementVO pageElementVO) {
		super.pageQuery(page,size,sort);
        Example.Builder builder = new Example.Builder(PageElement.class);
        if(pageElementVO != null){
        }
        Page<PageElement> all = (Page<PageElement>) pageElementMapper.selectByExample(builder.build());
        return ok(all.getTotal(),all.getResult());
    }

    @Override
    public PageElement insertVO(PageElementVO vo) {
		int i = pageElementMapper.selectCount(PageElement.builder().name(vo.getName()).build());
		if ( i > 0){
			throw new CustomException(ResultCode.DATA_ALREADY_EXISTED.getCode(),ResultCode.DATA_ALREADY_EXISTED.getMsg());
		}
		PageElement pageElement = vo.convertTo();
		this.insert(pageElement);
		return pageElement;
    }

    @Override
    public PageElement updateVO(PageElementVO vo) {
		this.update(vo.convertTo());
        return vo.convertTo();
    }
}


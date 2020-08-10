package com.leosanqing.index.service.impl;

import com.leosanqing.index.mapper.CarouselMapper;
import com.leosanqing.index.pojo.Carousel;
import com.leosanqing.index.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/5/6 上午10:58
 * @Package: com.leosanqing.index.service.impl
 * @Description: 首页服务实现类
 * @Version: 1.0
 */
@RestController
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {

        Example example = new Example(Carousel.class);
        example.orderBy("sort");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);

        return carouselMapper.selectByExample(example);
    }
}

package com.leosanqing.test.index.servcie;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.leosanqing.index.pojo.Carousel;
import com.leosanqing.index.service.CarouselService;
import com.leosanqing.test.index.BaseTest;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/8/26 上午9:54
 * @Package: com.leosanqing.test.item.controller
 * @Description: ItemController 测试
 * @Version: 1.0
 */

public class CarouselServiceTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    CarouselService carouselService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        carouselService = (CarouselService) AopProxyUtils.getSingletonTarget(carouselService);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void queryAllCarousel() {
        List<Carousel> carousels = carouselService.queryAll(1);

        Assert.assertEquals(4, carousels.size());
        Assert.assertEquals(1, (int) carousels.get(0).getIsShow());
        Assert.assertEquals("http://122.152.205.72:88/group1/M00/00/05/CpoxxF0ZmG-ALsPRAAEX2Gk9FUg848.png",
                carousels.get(0).getImageUrl());
    }
}

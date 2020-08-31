package com.leosanqing.test.index.servcie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.index.pojo.Carousel;
import com.leosanqing.index.pojo.Category;
import com.leosanqing.index.pojo.vo.CategoryVO;
import com.leosanqing.index.pojo.vo.NewItemsVO;
import com.leosanqing.index.service.CarouselService;
import com.leosanqing.index.service.CategoryService;
import com.leosanqing.test.index.BaseTest;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/8/27 下午4:15
 * @Package: com.leosanqing.test.index.servcie
 * @Description: 分类服务 测试类
 * @Version: 2.0
 */
public class CategoryServiceTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    CategoryService categoryService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        categoryService = (CategoryService) AopProxyUtils.getSingletonTarget(categoryService);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void queryAllRootLevelCat() {
        List<Category> categories = categoryService.queryAllRootLevelCat();
        Assert.assertEquals(10, categories.size());
        Assert.assertEquals("甜点/蛋糕", categories.get(0).getName());
    }

    @Test
    void getSubCatList() {
        List<CategoryVO> subCatList = categoryService.getSubCatList(2);
        Assert.assertEquals(19, subCatList.size());
        Assert.assertEquals("饼干", subCatList.get(0).getName());
    }

    @Test
    void getSixNewItemsLazy() {
        List<NewItemsVO> sixNewItemsLazy = categoryService.getSixNewItemsLazy(2);
        Assert.assertEquals(1, sixNewItemsLazy.size());
        Assert.assertEquals(6, sixNewItemsLazy.get(0).getSimpleItemList().size());
        Assert.assertEquals("bingan-1001", sixNewItemsLazy.get(0).getSimpleItemList().get(0).getItemId());
    }
}

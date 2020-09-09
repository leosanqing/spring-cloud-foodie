package com.leosanqing.test.index.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.index.controller.IndexController;
import com.leosanqing.index.pojo.Carousel;
import com.leosanqing.index.pojo.Category;
import com.leosanqing.index.pojo.vo.NewItemsVO;
import com.leosanqing.rpc.ResBody;
import com.leosanqing.test.index.BaseTest;
import com.leosanqing.utils.RedisOperator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/8/26 上午9:54
 * @Package: com.leosanqing.test.item.controller
 * @Description: ItemController 测试
 * @Version: 1.0
 */

public class IndexControllerTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    RedisOperator redisOperator;


    @InjectMocks
    @Autowired
    IndexController indexController;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        indexController = (IndexController) AopProxyUtils.getSingletonTarget(indexController);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void carousel() throws Exception {
        redisOperator.del(CAROUSEL);
        Assert.assertNull(redisOperator.get(CAROUSEL));
        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/index/carousel")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ResBody<List<Carousel>> resBody = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<ResBody<List<Carousel>>>() {
                }
        );
        List<Carousel> carouselList = resBody.getData();

        Assert.assertEquals(4, carouselList.size());
        Assert.assertEquals("nut-1004", carouselList.get(0).getItemId());

        String carousel = redisOperator.get(CAROUSEL);
        Assert.assertNotNull(carousel);
    }

    @Test
    void getCats() throws Exception {
        redisOperator.del(CATS);
        Assert.assertNull(redisOperator.get(CATS));

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/index/cats")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ResBody<List<Category>> resBody = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<ResBody<List<Category>>>() {
                }
        );

        List<Category> data = resBody.getData();
        Assert.assertEquals(10, data.size());

        Assert.assertNotNull(redisOperator.get(CATS));
    }

    @Test
    void subCats() throws Exception {

        redisOperator.del(SUB_CAT + ":" + SUB_CAT_ID);
        Assert.assertNull(redisOperator.get(SUB_CAT + ":" + SUB_CAT_ID));


        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/index/subCat/{rootCatId}", "1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ResBody<List<Category>> resBody = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<ResBody<List<Category>>>() {
                }
        );

        List<Category> data = resBody.getData();
        Assert.assertEquals(14, data.size());

        Assert.assertNotNull(redisOperator.get(SUB_CAT + ":" + SUB_CAT_ID));
    }

    @Test
    void getSixItems() throws Exception {

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/index/sixNewItems/{rootCatId}", 1)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ResBody<List<NewItemsVO>> resBody = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<ResBody<List<NewItemsVO>>>() {
                }
        );

        List<NewItemsVO> data = resBody.getData();
        Assert.assertEquals(6, data.get(0).getSimpleItemList().size());

    }
}

package com.leosanqing.test.item.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.item.controller.ItemController;
import com.leosanqing.item.pojo.vo.ShopcartVO;
import com.leosanqing.rpc.ResBody;
import com.leosanqing.test.item.BaseTest;
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

public class ItemControllerTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;


    @InjectMocks
    @Autowired
    ItemController itemController;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        itemController = (ItemController) AopProxyUtils.getSingletonTarget(itemController);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void query_item_info() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/items/info/{itemId}", "bingan-1001")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.data.item.catId").value(51)
                );
    }

    @Test
    void query_comment_level() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/items/commentLevel")
                                .param("itemId", "cake-1001")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.data.totalCounts").value(23)
                );
    }


    @Test
    void query_comments_count() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/items/comments")
                                .param("itemId", "cake-1001")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.data.total").value(23)
                );
    }


    @Test
    void query_items_by_spec_id() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/items/refresh")
                                .param("itemSpecIds", "bingan-1001-spec-1,bingan-1003-spec-2,bingan-1003-spec-3")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ResBody<List<ShopcartVO>> resBody = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ResBody<List<ShopcartVO>>>() {
                });

        List<ShopcartVO> shopcartVOS = resBody.getData();
        Assert.assertEquals(3, shopcartVOS.size());

        ShopcartVO shopcartVO = shopcartVOS.get(0);

        Assert.assertEquals("巧克力", shopcartVO.getSpecName());
        Assert.assertEquals("12000", shopcartVO.getPriceDiscount());

    }
}

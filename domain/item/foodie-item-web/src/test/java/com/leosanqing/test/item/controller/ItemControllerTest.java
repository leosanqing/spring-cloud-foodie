package com.leosanqing.test.item.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.item.controller.ItemController;
import com.leosanqing.item.pojo.vo.ShopcartVO;
import com.leosanqing.pojo.JSONResult;
import com.leosanqing.test.item.BaseTest;
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

import java.nio.charset.StandardCharsets;
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
    ItemController companyController;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        companyController = (ItemController) AopProxyUtils.getSingletonTarget(companyController);
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
                                .jsonPath("$.data.data.item.catId").value(51)
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
                                .jsonPath("$.data.data.totalCounts").value(23)
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
                                .jsonPath("$.data.data.total").value(23)
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


    }
}

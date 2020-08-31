package com.leosanqing.test.index.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.index.controller.IndexController;
import com.leosanqing.test.index.BaseTest;
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
    IndexController indexController;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        indexController = (IndexController) AopProxyUtils.getSingletonTarget(indexController);
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

}

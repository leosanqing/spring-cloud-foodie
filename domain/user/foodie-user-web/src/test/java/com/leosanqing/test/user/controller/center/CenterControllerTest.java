package com.leosanqing.test.user.controller.center;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.controller.BaseController;
import com.leosanqing.test.user.BaseTest;
import com.leosanqing.user.controller.center.CenterController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @Author: rtliu
 * @Date: 2020/8/28 上午11:22
 * @Package: com.leosanqing.test.user.controller.center
 * @Description: 用户中心 controller
 * @Version: 2.0
 */
public class CenterControllerTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;


    @InjectMocks
    @Autowired
    CenterController centerController;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        centerController = (CenterController) AopProxyUtils.getSingletonTarget(centerController);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void query_item_info() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/center/userInfo/")
                                .param("userId", USER_ID)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.data.data.username").value(USER_NAME)
                );
    }

}

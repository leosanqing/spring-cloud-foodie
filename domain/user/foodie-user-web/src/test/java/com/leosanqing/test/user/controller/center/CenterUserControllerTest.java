package com.leosanqing.test.user.controller.center;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.test.user.BaseTest;
import com.leosanqing.user.controller.center.CenterController;
import com.leosanqing.user.controller.center.CenterUserController;
import com.leosanqing.user.mapper.UsersMapper;
import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.bo.center.CenterUserBO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: rtliu
 * @Date: 2020/8/28 上午11:22
 * @Package: com.leosanqing.test.user.controller.center
 * @Description: 用户中心 controller
 * @Version: 2.0
 */
public class CenterUserControllerTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;


    @InjectMocks
    @Autowired
    CenterUserController centerUserController;

    @Resource
    UsersMapper usersMapper;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        centerUserController = (CenterUserController) AopProxyUtils.getSingletonTarget(centerUserController);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void updateUserInfo() throws Exception {
        Date date = new Date();
        CenterUserBO centerUserBO = new CenterUserBO();
        centerUserBO.setBirthday(date);

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/userInfo/update/")
                                .param("userId", USER_ID)
                                .content(mapper.writeValueAsString(centerUserBO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                                .jsonPath("$.message").value(USER_NAME)
                );

        Users users = usersMapper.selectById(USER_ID);
        Assert.assertEquals(users.getBirthday(), date);
    }

}

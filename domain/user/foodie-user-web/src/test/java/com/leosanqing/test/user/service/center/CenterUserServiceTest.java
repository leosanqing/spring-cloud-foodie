package com.leosanqing.test.user.service.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.leosanqing.test.user.BaseTest;
import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.bo.UserBO;
import com.leosanqing.user.pojo.bo.center.CenterUserBO;
import com.leosanqing.user.service.center.CenterUserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/8/26 上午9:54
 * @Package: com.leosanqing.test.item.controller
 * @Description: ItemController 测试
 * @Version: 1.0
 */

public class CenterUserServiceTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    CenterUserService centerUserService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        centerUserService = (CenterUserService) AopProxyUtils.getSingletonTarget(centerUserService);
        MockitoAnnotations.initMocks(this);
    }

    private final static String USER_NAME = "leosanqing";
    private final static String USER_ID = "19120779W7TK6800";
    private final static String FACE_URL = "http://localhost:8088/img/19120779W7TK6800/face-19120779W7TK6800" +
            ".jpg?t=20191216235414";


    @Test
    void queryUserInfo() {
        Users users = centerUserService.queryUserInfo(USER_ID);
        Assert.assertEquals(USER_NAME, users.getUsername());
    }

    @Test
    @Transactional
    void updateUserInfo() {
        Users users = centerUserService.queryUserInfo(USER_ID);
        String email = users.getEmail();

        CenterUserBO centerUserBO = new CenterUserBO();
        if (Strings.isNullOrEmpty(email)) {
            centerUserBO.setEmail("leosanqing@qq.com");
            centerUserService.updateUserInfo(USER_ID, centerUserBO);

            Users tempUser = centerUserService.queryUserInfo(USER_ID);
            Assert.assertEquals("leosanqing@qq.com", tempUser.getEmail());
        } else {
            centerUserBO.setEmail("");

            centerUserService.updateUserInfo(USER_ID, centerUserBO);
            Users tempUser = centerUserService.queryUserInfo(USER_ID);
            Assert.assertEquals("", tempUser.getEmail());
        }
    }


    @Test
    void updateUserFace() {
        Users users = centerUserService.updateUserFace(USER_ID, "1");
        Assert.assertEquals("1", users.getFace());
    }

}

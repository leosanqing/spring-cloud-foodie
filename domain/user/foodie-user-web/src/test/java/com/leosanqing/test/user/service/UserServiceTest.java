package com.leosanqing.test.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.test.user.BaseTest;
import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.bo.UserBO;
import com.leosanqing.user.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.n3r.idworker.Sid;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: rtliu
 * @Date: 2020/8/26 上午9:54
 * @Package: com.leosanqing.test.item.controller
 * @Description: ItemController 测试
 * @Version: 1.0
 */

public class UserServiceTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    UserService userService;

    @Autowired
    Sid sid;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        userService = (UserService) AopProxyUtils.getSingletonTarget(userService);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void queryUsernameIsExist() {
        Assert.assertTrue(userService.queryUsernameIsExist(USER_NAME));
        Assert.assertFalse(userService.queryUsernameIsExist(USER_NAME + 1));
    }

    @Test
    void createUser() {
        UserBO userBO = new UserBO();
        userBO.setUsername(sid.nextShort());
        userBO.setPassword(USER_NAME);
        userBO.setConfirmPassword(USER_NAME);
        Users user = userService.createUser(userBO);

        Assert.assertTrue(userService.queryUsernameIsExist(user.getUsername()));
    }

    @Test
    void queryUserForLogin() throws Exception {
        Users users = userService.queryUserForLogin(USER_NAME, PASSWORD);
        Assert.assertNotNull(users);
    }


}

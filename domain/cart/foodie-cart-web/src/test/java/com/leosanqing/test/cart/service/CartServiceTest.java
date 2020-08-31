package com.leosanqing.test.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.cart.service.CartService;
import com.leosanqing.pojo.ShopCartBO;
import com.leosanqing.test.cart.BaseTest;
import com.leosanqing.utils.JsonUtils;
import com.leosanqing.utils.RedisOperator;
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
 * @Date: 2020/8/26 上午9:54
 * @Package: com.leosanqing.test.item.controller
 * @Description: ItemController 测试
 * @Version: 1.0
 */

public class CartServiceTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    CartService cartService;

    @Autowired
    RedisOperator redisOperator;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        cartService = (CartService) AopProxyUtils.getSingletonTarget(cartService);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void addItemToCart() {
        ShopCartBO cartBO = ShopCartBO
                .builder()
                .itemId(ITEM_ID)
                .buyCounts(1)
                .itemImgUrl(ITEM_IMG_URL)
                .itemName(ITEM_NAME)
                .specId("1")
                .specName("原味")
                .priceNormal("200000")
                .priceDiscount("18000")
                .build();
        cartService.addItemToCart(USER_ID, cartBO);

        String s = redisOperator.get(SHOP_CART + ":" + USER_ID);

        Assert.assertNotNull(JsonUtils.jsonToList(s, ShopCartBO.class));
    }

    @Test
    void removeItemFromCart() {
        cartService.removeItemFromCart(USER_ID, "1");
    }

    @Test
    void clearCart() {
        cartService.clearCart(USER_ID);

        String s = redisOperator.get(SHOP_CART + ":" + USER_ID);
        Assert.assertNull(s);
    }


}

package com.leosanqing.test.cart.center;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.leosanqing.cart.controller.ShopCartController;
import com.leosanqing.pojo.ShopCartBO;
import com.leosanqing.test.cart.BaseTest;
import com.leosanqing.utils.JsonUtils;
import com.leosanqing.utils.RedisOperator;
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

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/8/28 上午11:22
 * @Package: com.leosanqing.test.user.controller.center
 * @Description: 用户中心 controller
 * @Version: 2.0
 */
public class ShopCartControllerTest extends BaseTest {

    @Autowired
    ObjectMapper mapper;

    @InjectMocks
    @Autowired
    ShopCartController shopCartController;

    @Autowired
    RedisOperator redisOperator;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        shopCartController = (ShopCartController) AopProxyUtils.getSingletonTarget(shopCartController);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void add() throws Exception {
        String s = redisOperator.get(SHOP_CART + ":" + USER_ID);
        if (Strings.isNullOrEmpty(s)) {
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

            mockMvc
                    .perform(
                            MockMvcRequestBuilders
                                    .post("/shopcart/add/")
                                    .param("userId", USER_ID)
                                    .content(mapper.writeValueAsString(cartBO))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk());
            return;
        }


        List<ShopCartBO> shopCartBOS = JsonUtils.jsonToList(s, ShopCartBO.class);


        if (shopCartBOS == null || shopCartBOS.size() == 0) {
            return;
        }
        for (ShopCartBO shopCartBO : shopCartBOS) {
            if (!shopCartBO.getSpecId().equals("1")) {
                continue;
            }

            shopCartBO.setBuyCounts(shopCartBO.getBuyCounts() + 1);


            mockMvc
                    .perform(
                            MockMvcRequestBuilders
                                    .post("/shopcart/add/")
                                    .param("userId", USER_ID)
                                    .content(mapper.writeValueAsString(shopCartBO))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk());

        }

        String s1 = redisOperator.get(SHOP_CART + ":" + USER_ID);
        Assert.assertNotNull(s1);

        List<ShopCartBO> shopCartBOS1 = JsonUtils.jsonToList(s1, ShopCartBO.class);
        Assert.assertNotNull(shopCartBOS1);

        Assert.assertEquals(shopCartBOS.size(), shopCartBOS1.size());

        for (int i = 0; i < shopCartBOS.size(); i++) {
            ShopCartBO shopCartBO = shopCartBOS.get(i);
            ShopCartBO shopCartBO1 = shopCartBOS1.get(i);

            Assert.assertEquals(shopCartBO.getSpecId(), shopCartBO1.getSpecId());
            Assert.assertEquals((shopCartBO.getBuyCounts() + 1), shopCartBO1.getBuyCounts().intValue());
        }


    }

    @Test
    void clearCart() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/shopcart/clear_cart/")
                                .param("userId", USER_ID)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());


        String s = redisOperator.get(SHOP_CART + ":" + USER_ID);

        Assert.assertNull(s);
    }

    @Test
    void del() throws Exception {
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

        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/shopcart/add/")
                                .param("userId", USER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(cartBO))
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());


        mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/shopcart/del/")
                                .param("userId", USER_ID)
                                .content(mapper.writeValueAsString(cartBO.getSpecId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        String s = redisOperator.get(SHOP_CART + ":" + USER_ID);
        Assert.assertNotNull(s);

        List<ShopCartBO> shopCartBOS = JsonUtils.jsonToList(s, ShopCartBO.class);

        Assert.assertNotNull(shopCartBOS);
        for (ShopCartBO shopCartBO : shopCartBOS) {
            Assert.assertEquals(shopCartBO.getSpecId(), cartBO.getSpecId());
        }
    }

}

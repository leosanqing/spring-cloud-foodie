package com.leosanqing.test.order.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.order.controller.OrdersController;
import com.leosanqing.order.mapper.OrdersMapper;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.bo.SubmitOrderBO;
import com.leosanqing.pojo.ShopCartBO;
import com.leosanqing.rpc.ResBody;
import com.leosanqing.test.order.BaseTest;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/9/2 下午4:51
 * @Package: com.leosanqing.test.order
 * @Description: 订单 Controller test
 * @Version: 1.0
 */
public class OrderControllerTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;

    @InjectMocks
    @Autowired
    OrdersController ordersController;

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    OrdersMapper ordersMapper;

    @Resource


    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        ordersController = (OrdersController) AopProxyUtils.getSingletonTarget(ordersController);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() throws Exception {

        List<ShopCartBO> shopCartBOS = new ArrayList<>();
        ShopCartBO shopCartBO1 = ShopCartBO.builder()
                .priceDiscount("100")
                .priceNormal("1000")
                .specName("巧克力")
                .specId("bingan-1001-spec-1")
                .itemName("【天天吃货】彩虹马卡龙 下午茶 美眉最爱")
                .itemId("bingan-1001")
                .itemImgUrl("http://122.152.205.72:88/foodie/bingan-1001/img1.png")
                .buyCounts(5)
                .build();

        shopCartBOS.add(shopCartBO1);
        redisOperator.del(SHOP_CART + ":" + USER_ID);
        redisOperator.set(SHOP_CART + ":" + USER_ID, JsonUtils.objectToJson(shopCartBOS));

        SubmitOrderBO submitOrderBO = SubmitOrderBO.builder()
                .addressId(ADDRESS_ID)
                .itemSpecIds("bingan-1001-spec-1")
                .leftMsg("免单")
                .payMethod(1)
                .userId(USER_ID)
                .build();

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/orders/create/")
                                .param("userId", USER_ID)
                                .content(mapper.writeValueAsString(submitOrderBO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String s = redisOperator.get(SHOP_CART + ":" + USER_ID);
        List<ShopCartBO> shopCartBOS1 = JsonUtils.jsonToList(s, ShopCartBO.class);

        Assert.assertEquals(shopCartBOS1.size(), 0);

        ResBody<String> resBody = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ResBody<String>>() {
                }
        );

        Orders orders = ordersMapper.selectOne(
                Wrappers.lambdaQuery(Orders.class)
                        .eq(Orders::getId, resBody.getData())
        );

        Assert.assertEquals(USER_ID, orders.getUserId());
    }

    @Test
    void getPaidOrderInfo() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/orders/getPaidOrderInfo/")
                                .param("orderId", ORDER_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();


        ResBody<OrderStatus> resBody = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<ResBody<OrderStatus>>() {
                }
        );

        OrderStatus orderStatus = resBody.getData();
        Assert.assertEquals(40, (int) orderStatus.getOrderStatus());

    }


}

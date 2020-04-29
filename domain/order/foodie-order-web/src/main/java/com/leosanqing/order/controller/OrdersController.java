package com.leosanqing.order.controller;

import com.leosanqing.controller.BaseController;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.bo.PlaceOrderBO;
import com.leosanqing.order.pojo.bo.SubmitOrderBO;
import com.leosanqing.order.pojo.vo.OrderVO;
import com.leosanqing.order.service.OrderService;
import com.leosanqing.pojo.JSONResult;
import com.leosanqing.pojo.ShopCartBO;
import com.leosanqing.utils.CookieUtils;
import com.leosanqing.utils.JsonUtils;
import com.leosanqing.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
@Slf4j
public class OrdersController extends BaseController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response) {

        final String shopCartStr = redisOperator.get(SHOP_CART + ":" + submitOrderBO.getUserId());
        if (StringUtils.isNotBlank(shopCartStr)) {
            return JSONResult.errorMsg("购物车数据不正确");
        }
        List<ShopCartBO> shopCartBOList = JsonUtils.jsonToList(shopCartStr, ShopCartBO.class);


        // 1.创建订单
        OrderVO orderVO = orderService.createOrder(new PlaceOrderBO(submitOrderBO, shopCartBOList));
        String orderId = orderVO.getOrderId();

        // 2.创建订单以后，移除购物车中已结算的商品
        if (shopCartBOList != null) {
            shopCartBOList.removeAll(orderVO.getToBeRemovedList());
        }
        redisOperator.set(SHOP_CART + ":" + submitOrderBO.getUserId(), JsonUtils.objectToJson(shopCartBOList));

        CookieUtils.setCookie(request, response, SHOP_CART, JsonUtils.objectToJson(shopCartBOList), true);

        // TODO 整合Redis之后
        // 3.像支付中心发送当前订单，用于保存支付中心的订单数据
        System.out.println(submitOrderBO.toString());
        return JSONResult.ok(orderId);

    }

//    @PostMapping("notifyMerchantOrderPaid")
//    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
//        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
//        return HttpStatus.OK.value();
//    }

    @PostMapping("getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(@RequestParam String orderId) {

        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONResult.ok(orderStatus);
    }
}

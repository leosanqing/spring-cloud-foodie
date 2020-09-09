package com.leosanqing.order.controller.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.vo.MyOrdersVO;
import com.leosanqing.order.pojo.vo.OrderStatusCountsVO;
import com.leosanqing.order.service.center.MyOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: leosanqing
 * @Date: 2019-12-15 20:00
 * @Package: com.leosanqing.controller.center
 * @Description: 用户中心订单Controller
 */
@Api(value = "我的订单-用户中心", tags = {"我的订单-用户中心展示的相关接口"})
@RestController
@RequestMapping("myorders")
public class MyOrdersController {

    @Autowired
    private MyOrdersService myOrdersService;

    @PostMapping("query")
    @ApiOperation(value = "查询我的订单", notes = "查询我的订单", httpMethod = "POST")
    public IPage<MyOrdersVO> queryUserInfo(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态")
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "当前页数")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "页面展示条数")
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        if (StringUtils.isBlank(userId)) {
            throw new RuntimeException("用户名id为空");
        }

        return myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
    }


    @PostMapping("trend")
    @ApiOperation(value = "查询我的订单", notes = "查询我的订单", httpMethod = "POST")
    public IPage<OrderStatus> getTrend(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "page", value = "当前页数")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "页面展示条数")
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        if (StringUtils.isBlank(userId)) {
            throw new RuntimeException("用户名id为空");
        }

        return myOrdersService.getOrdersTrend(userId, page, pageSize);
    }


    // 商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public void deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) {

        if (StringUtils.isBlank(orderId)) {
            throw new RuntimeException("订单ID不能为空");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
    }


    @ApiOperation(value = "确认收货", notes = "确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public void confirmReceive(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) {

        checkUserOrder(userId, orderId);
        boolean isSuccess = myOrdersService.updateReceiveOrderStatus(orderId);
        if (!isSuccess) {
            throw new RuntimeException("确认收货失败");
        }
    }


    @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public void deleteOrder(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) {

        checkUserOrder(userId, orderId);

        final boolean isSuccess = myOrdersService.deleteOrder(userId, orderId);
        if (!isSuccess) {
            throw new RuntimeException("删除订单失败");
        }
    }


    @ApiOperation(value = "获得订单状态数概况", notes = "获得订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public OrderStatusCountsVO statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new RuntimeException("用户Id为空");
        }

        return myOrdersService.getOrderStatusCounts(userId);
    }

    /**
     * 用于验证是否为用户订单，防止恶意查询
     *
     * @param userId
     * @param orderId
     * @return
     */
    private void checkUserOrder(String userId, String orderId) {
        if (StringUtils.isBlank(userId)) {
            throw new RuntimeException("用户ID不能为空");
        }
        if (StringUtils.isBlank(orderId)) {
            throw new RuntimeException("订单ID不能为空");
        }
        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (orders == null) {
            throw new RuntimeException("查询到订单为空");
        }
    }


}

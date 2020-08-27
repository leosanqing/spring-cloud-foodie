package com.leosanqing.order.controller.center;

import com.leosanqing.enums.YesOrNo;

import com.leosanqing.item.service.ItemCommentsService;
import com.leosanqing.order.pojo.OrderItems;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.bo.center.OrderItemsCommentBO;
import com.leosanqing.order.service.center.MyCommentsService;
import com.leosanqing.order.service.center.MyOrdersService;
import com.leosanqing.pojo.JSONResult;
import com.leosanqing.pojo.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2019/12/22 下午4:14
 * @Package: com.leosanqing.controller.center
 * @Description: 我的订单接口
 */
@Api(value = "我的订单-我的评价", tags = {"我的评价-用户中心展示的相关接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController {

    @Autowired
    private MyCommentsService myCommentsService;

    @Autowired
    private LoadBalancerClient client;
    @Autowired
    private MyOrdersService myOrdersService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ItemCommentsService itemCommentsService;

    @PostMapping("pending")
    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    public JSONResult pending(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单Id")
            @RequestParam String orderId

    ) {
        final JSONResult result = checkUserOrder(userId, orderId);
        if (result.getStatus() != HttpStatus.OK.value()) {

            return result;
        }

        final Orders orders = (Orders) result.getData();
        if (orders.getIsComment() == YesOrNo.YES.type) {
            return JSONResult.errorMsg("商品已经评价过");
        }

        final List<OrderItems> orderItems = myCommentsService.queryPendingComment(orderId);
        return JSONResult.ok(orderItems);
    }


    @PostMapping("query")
    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    public JSONResult queryMyComment(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "page", value = "当前页数")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "页面展示条数")
            @RequestParam(defaultValue = "10") Integer pageSize

    ) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户Id为空");
        }


//        ServiceInstance instance = client.choose("FOODIE-ITEM-SERVICE");
//        String target = String.format("http://%s:%s/item-comments-api/myComments" +
//                        "?userId=%s&page=%s&pageSize=%s",
//                instance.getHost(),
//                instance.getPort(),
//                userId,
//                page,
//                pageSize);
//        PagedGridResult grid = restTemplate.getForObject(target, PagedGridResult.class);
//        return JSONResult.ok(grid);

        return JSONResult.ok(itemCommentsService.queryMyComments(userId, page, pageSize));
    }


    @PostMapping("saveList")
    @ApiOperation(value = "保存评价列表", notes = "保存评价列表", httpMethod = "POST")
    public JSONResult saveList(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单Id")
            @RequestParam String orderId,
            @ApiParam(name = "orderItemList", value = "订单项列表")
            @RequestBody List<OrderItemsCommentBO> orderItemList

    ) {
        final JSONResult result = checkUserOrder(userId, orderId);
        if (result.getStatus() != HttpStatus.OK.value()) {
            return result;
        }
        if (orderItemList == null || orderItemList.isEmpty()) {
            return JSONResult.errorMsg("评价列表为空");
        }

        myCommentsService.saveComments(userId, orderId, orderItemList);
        return JSONResult.ok();
    }


    /**
     * 用于验证是否为用户订单，防止恶意查询
     *
     * @param userId
     * @param orderId
     * @return
     */
    private JSONResult checkUserOrder(String userId, String orderId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户ID不能为空");
        }
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("订单ID不能为空");
        }
        final Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (orders == null) {
            return JSONResult.errorMsg("查询到订单为空");
        }
        return JSONResult.ok(orders);
    }


}

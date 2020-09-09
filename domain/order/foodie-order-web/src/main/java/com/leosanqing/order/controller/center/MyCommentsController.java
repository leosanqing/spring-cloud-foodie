package com.leosanqing.order.controller.center;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leosanqing.enums.YesOrNo;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import com.leosanqing.item.service.ItemCommentsService;
import com.leosanqing.order.pojo.OrderItems;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.bo.center.OrderItemsCommentBO;
import com.leosanqing.order.service.center.MyCommentsService;
import com.leosanqing.order.service.center.MyOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //    @Autowired
//    private LoadBalancerClient client;
    @Autowired
    private MyOrdersService myOrdersService;
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private ItemCommentsService itemCommentsService;


    @PostMapping("pending")
    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    public List<OrderItems> pending(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单Id")
            @RequestParam String orderId

    ) {
        Orders orders = checkUserOrder(userId, orderId);
        if (orders.getIsComment() == YesOrNo.YES.type) {
            throw new RuntimeException("商品已经评价过");
        }

        return myCommentsService.queryPendingComment(orderId);
    }


    @PostMapping("query")
    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    public IPage<MyCommentVO> queryMyComment(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "page", value = "当前页数")
            @RequestParam(defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "页面展示条数")
            @RequestParam(defaultValue = "10") Integer pageSize

    ) {
        if (StringUtils.isBlank(userId)) {
            throw new RuntimeException("用户Id为空");
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

        return itemCommentsService.queryMyComments(userId, page, pageSize);
    }


    @PostMapping("saveList")
    @ApiOperation(value = "保存评价列表", notes = "保存评价列表", httpMethod = "POST")
    public void saveList(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单Id")
            @RequestParam String orderId,
            @ApiParam(name = "orderItemList", value = "订单项列表")
            @RequestBody List<OrderItemsCommentBO> orderItemList

    ) {
        checkUserOrder(userId, orderId);

        if (orderItemList == null || orderItemList.isEmpty()) {
            throw new RuntimeException("评价列表为空");
        }

        myCommentsService.saveComments(userId, orderId, orderItemList);
    }


    /**
     * 用于验证是否为用户订单，防止恶意查询
     *
     * @param userId
     * @param orderId
     * @return
     */
    private Orders checkUserOrder(String userId, String orderId) {
        if (StringUtils.isBlank(userId)) {
            throw new RuntimeException("用户ID不能为空");
        }
        if (StringUtils.isBlank(orderId)) {
            throw new RuntimeException("订单ID不能为空");
        }
        final Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (orders == null) {
            throw new RuntimeException("查询到订单为空");
        }
        return orders;
    }


}

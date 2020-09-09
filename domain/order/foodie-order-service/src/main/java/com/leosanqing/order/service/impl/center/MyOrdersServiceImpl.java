package com.leosanqing.order.service.impl.center;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leosanqing.enums.YesOrNo;
import com.leosanqing.order.mapper.OrderStatusMapper;
import com.leosanqing.order.mapper.OrdersMapper;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.vo.MyOrdersVO;
import com.leosanqing.order.pojo.vo.OrderStatusCountsVO;
import com.leosanqing.order.service.center.MyOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MyOrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements MyOrdersService {

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public IPage<MyOrdersVO> queryMyOrders(String userId, Integer orderStatus,
                                           Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        return baseMapper.queryMyOrders(map, new Page(page, pageSize));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatus.OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        orderStatusMapper.update(
                updateOrder,
                Wrappers.lambdaUpdate(OrderStatus.class)
                        .eq(OrderStatus::getOrderId, orderId)
                        .eq(OrderStatus::getOrderStatus, OrderStatus.OrderStatusEnum.WAIT_DELIVER.type)
        );
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        return lambdaQuery()
                .eq(Orders::getId, orderId)
                .eq(Orders::getUserId, userId)
                .eq(Orders::getIsDelete, YesOrNo.NO.type)
                .one();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatus.OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());

        return 1 == orderStatusMapper.update(
                updateOrder,
                Wrappers
                        .lambdaUpdate(OrderStatus.class)
                        .eq(OrderStatus::getOrderId, orderId)
                        .eq(OrderStatus::getOrderStatus, OrderStatus.OrderStatusEnum.WAIT_RECEIVE.type)
        );

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {

        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNo.YES.type);
        updateOrder.setUpdatedTime(new Date());

        return lambdaUpdate()
                .eq(Orders::getId, orderId)
                .eq(Orders::getUserId, userId)
                .update();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", OrderStatus.OrderStatusEnum.WAIT_PAY.type);

        int waitPayCounts = baseMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatus.OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = baseMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatus.OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = baseMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatus.OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = baseMapper.getMyOrderStatusCounts(map);

        return new OrderStatusCountsVO(
                waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts
        );
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public IPage<OrderStatus> getOrdersTrend(String userId, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        return baseMapper.getMyOrderTrend(map, new Page(page, pageSize));

    }

    @Override
    public Orders checkUserOrder(@RequestParam("userId") String userId,
                                 @RequestParam("orderId") String orderId) {
        Orders order = queryMyOrder(userId, orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        return order;
    }
}

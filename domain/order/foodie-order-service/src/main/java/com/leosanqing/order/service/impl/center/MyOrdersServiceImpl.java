package com.leosanqing.order.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.leosanqing.enums.YesOrNo;
import com.leosanqing.order.mapper.OrderStatusMapper;
import com.leosanqing.order.mapper.OrdersMapper;
import com.leosanqing.order.mapper.OrdersMapperCustom;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.vo.MyOrdersVO;
import com.leosanqing.order.pojo.vo.OrderStatusCountsVO;
import com.leosanqing.order.service.center.MyOrdersService;
import com.leosanqing.pojo.JSONResult;
import com.leosanqing.pojo.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyOrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements MyOrdersService {

    @Autowired
    public OrdersMapperCustom ordersMapperCustom;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public IPage<Orders> queryMyOrders(
            String userId, Integer orderStatus,
            Integer page, Integer pageSize
    ) {
        return baseMapper.listOrders(userId, orderStatus, new Page<>(page, pageSize));
    }

//    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
//        PageInfo<?> pageList = new PageInfo<>(list);
//        PagedGridResult grid = new PagedGridResult();
//        grid.setPage(page);
//        grid.setRows(list);
//        grid.setTotal(pageList.getPages());
//        grid.setRecords(pageList.getTotal());
//        return grid;
//    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatus.OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());

        LambdaUpdateWrapper<OrderStatus> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OrderStatus::getOrderId, orderId)
                .eq(OrderStatus::getOrderStatus, OrderStatus.OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.update(updateOrder, wrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        return lambdaQuery()
                .eq(Orders::getUserId, userId)
                .eq(Orders::getId, orderId)
                .eq(Orders::getIsDelete, YesOrNo.NO.type)
                .one();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {

        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatus.OrderStatusEnum.SUCCESS.type);
        updateOrder.setSuccessTime(new Date());

        LambdaUpdateWrapper<OrderStatus> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OrderStatus::getOrderId, orderId)
                .eq(OrderStatus::getOrderStatus, OrderStatus.OrderStatusEnum.WAIT_RECEIVE.type)
                .set(OrderStatus::getOrderStatus, OrderStatus.OrderStatusEnum.SUCCESS.type)
                .set(OrderStatus::getSuccessTime, new Date());

        return 1 == orderStatusMapper.update(updateOrder, wrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {

        return lambdaUpdate()
                .eq(Orders::getId, orderId)
                .eq(Orders::getUserId, userId)
                .set(Orders::getIsDelete, YesOrNo.YES.type)
                .set(Orders::getUpdatedTime, new Date())
                .update();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {

        int waitPayCounts = ordersMapper
                .getMyOrderStatusCounts(
                        userId,
                        OrderStatus.OrderStatusEnum.WAIT_PAY.type,
                        null
                );

        int waitDeliverCounts = ordersMapper
                .getMyOrderStatusCounts(
                        userId,
                        OrderStatus.OrderStatusEnum.WAIT_DELIVER.type,
                        null
                );

        int waitReceiveCounts = ordersMapper
                .getMyOrderStatusCounts(
                        userId,
                        OrderStatus.OrderStatusEnum.WAIT_RECEIVE.type,
                        null
                );


        int waitCommentCounts = ordersMapper
                .getMyOrderStatusCounts(
                        userId,
                        OrderStatus.OrderStatusEnum.SUCCESS.type,
                        YesOrNo.NO.type
                );

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

        return ordersMapper.getMyOrderTrend(userId, new Page(page, pageSize));

    }

    @Override
    public JSONResult checkUserOrder(@RequestParam("userId") String userId,
                                     @RequestParam("orderId") String orderId) {
        Orders order = queryMyOrder(userId, orderId);
        if (order == null) {
            return JSONResult.errorMap("订单不存在！！！");
        }
        return JSONResult.ok(order);
    }
}

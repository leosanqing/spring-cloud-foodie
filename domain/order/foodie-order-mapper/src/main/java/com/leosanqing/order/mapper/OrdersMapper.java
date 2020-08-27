package com.leosanqing.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 查询我的订单
     *
     * @param map
     * @param page
     * @return
     */
    IPage<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map, Page page);

    /**
     * 查询各类订单状态
     *
     * @param map
     * @return
     */
    int getMyOrderStatusCounts(@Param("paramsMap") Map<String, Object> map);

    /**
     * 查询订单动态
     *
     * @param map
     * @return
     */
    IPage<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map, Page page);
}
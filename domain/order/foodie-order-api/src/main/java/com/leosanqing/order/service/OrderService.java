package com.leosanqing.order.service;


import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.bo.PlaceOrderBO;
import com.leosanqing.order.pojo.vo.OrderVO;
import org.springframework.web.bind.annotation.*;

@RequestMapping("order-api")
public interface OrderService {

    /**
     * 用于创建订单相关信息
     *
     * @param orderBO
     */
    @PostMapping("placeOrder")
    OrderVO createOrder(@RequestBody PlaceOrderBO orderBO);

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param orderStatus
     */
    @PostMapping("updateStatus")
    void updateOrderStatus(@RequestParam("orderId") String orderId,
                           @RequestParam("orderStatus") Integer orderStatus);

    /**
     * 查询订单状态
     *
     * @param orderId
     * @return
     */
    @GetMapping("orderStatus")
    OrderStatus queryOrderStatusInfo(@RequestParam("orderId") String orderId);

    /**
     * 关闭超时未支付订单
     */
    @PostMapping("closePendingOrders")
    void closeOrder();

}

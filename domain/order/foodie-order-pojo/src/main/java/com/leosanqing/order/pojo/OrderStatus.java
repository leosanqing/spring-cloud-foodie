package com.leosanqing.order.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "order_status")
@Builder
@Data
@NoArgsConstructor
public class OrderStatus {
    /**
     * 订单ID;对应订单表的主键id
     */
    @Id
    @Column(name = "order_id")
    private String orderId;

    /**
     * 订单状态
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 订单创建时间;对应[10:待付款]状态
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 支付成功时间;对应[20:已付款，待发货]状态
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 发货时间;对应[30：已发货，待收货]状态
     */
    @Column(name = "deliver_time")
    private Date deliverTime;

    /**
     * 交易成功时间;对应[40：交易成功]状态
     */
    @Column(name = "success_time")
    private Date successTime;

    /**
     * 交易关闭时间;对应[50：交易关闭]状态
     */
    @Column(name = "close_time")
    private Date closeTime;

    /**
     * 留言时间;用户在交易成功后的留言时间
     */
    @Column(name = "comment_time")
    private Date commentTime;


    public enum OrderStatusEnum {
        WAIT_PAY(10, "待付款"),
        WAIT_DELIVER(20, "待发货"),
        WAIT_RECEIVE(30, "待收货"),
        SUCCESS(40, "交易成功"),
        CLOSE(50, "交易失败");


        public final Integer type;
        public final String value;

        OrderStatusEnum(Integer type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}
package com.leosanqing.order.pojo.vo;

import lombok.Data;

/**
 * 订单状态概览数量VO
 */
@Data
public class OrderStatusCountsVO {

    private Integer waitPayCounts;
    private Integer waitDeliverCounts;
    private Integer waitReceiveCounts;
    private Integer waitCommentCounts;

}
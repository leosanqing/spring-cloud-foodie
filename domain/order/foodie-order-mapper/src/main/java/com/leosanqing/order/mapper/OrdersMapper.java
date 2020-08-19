package com.leosanqing.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.Objects;

public interface OrdersMapper extends BaseMapper<Orders> {

    static String listDealSql(String userId, Integer orderStatus, Page page) {
        return new SQL() {{

            SELECT(
                    "od.id as orderId,\n" +
                            "        od.created_time as createdTime,\n" +
                            "        od.pay_method as payMethod,\n" +
                            "        od.real_pay_amount as realPayAmount,\n" +
                            "        od.post_amount as postAmount,\n" +
                            "        os.order_status as orderStatus,\n" +
                            "        oi.item_id as itemId,\n" +
                            "        oi.item_name as itemName,\n" +
                            "        oi.item_img as itemImg,\n" +
                            "        oi.item_spec_name as itemSpecName,\n" +
                            "        oi.buy_counts as buyCounts,\n" +
                            "        oi.price as price,\n" +
                            "        od.is_comment as isComment"
            );

            FROM(" orders od ");
            INNER_JOIN(
                    "order_status os ON od.id = os.order_id",
                    "order_items oi ON od.id = oi.order_id"
            );

            WHERE(
                    "od.user_id = #{userId}\n" +
                            "AND od.is_delete = 0"
            );
            if (Objects.nonNull(orderStatus)) {
                WHERE("and os.order_status = #{orderStatus}");
            }

            ORDER_BY("od.updated_time ASC");

        }}.toString();
    }

    @SelectProvider(type = OrdersMapper.class, method = "listDealSql")
    IPage<Orders> listOrders(
            @Param("userId") String userId,
            @Param("orderStatus") Integer orderStatus,
            @Param("page") Page page
    );


    static String getMyOrderStatusCountsSql(String userId, Integer type, Integer isComment) {
        return new SQL() {{

            SELECT("count(1)");

            FROM(" orders o ");
            INNER_JOIN(
                    "order_status os on o.id = os.order_id");

            WHERE("o.user_id = #{userId}");
            if (Objects.nonNull(isComment)) {
                WHERE("o.is_comment = #{isComment}");
            }

        }}.toString();
    }

    @SelectProvider(type = OrdersMapper.class, method = "getMyOrderStatusCountsSql")
    Integer getMyOrderStatusCounts(
            @Param("userId") String userId,
            @Param("type") Integer type,
            @Param("isComment") Integer isComment
    );


    static String getMyOrderStatusCountsSql(String userId, Page page) {
        return new SQL() {{

            SELECT(
                    "os.order_id as orderId,\n" +
                            "    os.order_status as orderStatus,\n" +
                            "    os.created_time as createdTime,\n" +
                            "    os.pay_time as payTime,\n" +
                            "    os.deliver_time as deliverTime,\n" +
                            "    os.success_time as successTime,\n" +
                            "    os.close_time as closeTime,\n" +
                            "    os.comment_time as commentTime"
            );

            FROM(" orders o ");
            INNER_JOIN("order_status os ON o.id = os.order_id");

            WHERE(
                    "o.is_delete = 0 \n" +
                            "AND o.user_id = #{userId} \n" +
                            "AND os.order_status in (20, 30, 40)"
            );

            ORDER_BY("os.order_id");

        }}.toString();
    }

    @SelectProvider(type = OrdersMapper.class, method = "getMyOrderTrendSql")
    IPage<OrderStatus> getMyOrderTrend(@Param("userId") String userId, @Param("page") Page page);
}
package com.leosanqing.order.service.impl.center;


import com.leosanqing.enums.YesOrNo;
import com.leosanqing.item.service.ItemCommentsService;
import com.leosanqing.order.fallback.itemservice.ItemCommentsFeignClient;
import com.leosanqing.order.mapper.OrderItemsMapper;
import com.leosanqing.order.mapper.OrderStatusMapper;
import com.leosanqing.order.mapper.OrdersMapper;
import com.leosanqing.order.pojo.OrderItems;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.bo.center.OrderItemsCommentBO;
import com.leosanqing.order.service.center.MyCommentsService;
import com.leosanqing.service.BaseService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    //    @Autowired
//    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;
    // TODO feign章节里改成item-api
    @Autowired
//    private ItemCommentsService itemCommentsService;
    private ItemCommentsFeignClient itemCommentsService;
//    @Autowired
//    private LoadBalancerClient client;
//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);

        // 使用feign接口调用
        itemCommentsService.saveComments(map);

//        ServiceInstance instance = client.choose("FOODIE-ITEM-SERVICE");
//        String url = String.format("http://%s:%s/item-comments-api/saveComments",
//                instance.getHost(),
//                instance.getPort());
//        restTemplate.postForLocation(url, map);


        // 2. 修改订单表改已评价 orders
        Orders order = Orders.builder()
                .id(orderId)
                .isComment(YesOrNo.YES.type)
                .build();
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(orderId)
                .commentTime(new Date())
                .build();
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    //  移到了itemCommentService
//    @Transactional(propagation = Propagation.SUPPORTS)
//    @Override
//    public PagedGridResult queryMyComments(String userId,
//                                           Integer page,
//                                           Integer pageSize) {
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", userId);
//
//        PageHelper.startPage(page, pageSize);
//        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);
//
//        return setterPagedGrid(list, page);
//    }
}

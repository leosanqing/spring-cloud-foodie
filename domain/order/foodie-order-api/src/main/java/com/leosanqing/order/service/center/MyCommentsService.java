package com.leosanqing.order.service.center;


import com.leosanqing.order.pojo.OrderItems;
import com.leosanqing.order.pojo.bo.center.OrderItemsCommentBO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("order-comments-api")
public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品
     *
     * @param orderId
     * @return
     */
    @GetMapping("orderItems")
    List<OrderItems> queryPendingComment(@RequestParam("orderId") String orderId);

    /**
     * 保存用户的评论
     *
     * @param orderId
     * @param userId
     * @param commentList
     */
    @PostMapping("saveOrderComments")
    void saveComments(@RequestParam("orderId") String orderId,
                      @RequestParam("userId") String userId,
                      @RequestBody List<OrderItemsCommentBO> commentList);


    //  移到了itemCommentsService里
//    /**
//     * 我的评价查询 分页
//     * @param userId
//     * @param page
//     * @param pageSize
//     * @return
//     */
//    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}

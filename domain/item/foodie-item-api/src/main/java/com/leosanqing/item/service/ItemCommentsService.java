package com.leosanqing.item.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: rtliu
 * @Date: 2020/4/27 上午10:46
 * @Package: com.leosanqing.item.service
 * @Description: 商品评价接口
 * @Version: 1.0
 */
@RequestMapping("item-comments-api")
@FeignClient("foodie-item-service")
public interface ItemCommentsService {
    /**
     * 保存订单评价列表
     *
     * @param map
     */
    @PostMapping("saveComments")
//    void saveComments(String userId, String orderId, List<OrderItemsCommentBO> orderItemList);
    void saveComments(@RequestBody Map<String, Object> map);


    /**
     * 查询我的评价列表
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("myComments")
    IPage<MyCommentVO> queryMyComments(
            @RequestParam("userId") String userId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    );
}

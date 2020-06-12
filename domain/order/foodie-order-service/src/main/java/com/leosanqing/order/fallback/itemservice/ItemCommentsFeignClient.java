package com.leosanqing.order.fallback.itemservice;

import com.leosanqing.item.service.ItemCommentsService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: rtliu
 * @Date: 2020/6/12 下午3:18
 * @Package: com.leosanqing.order.fallback.itemservice
 * @Description: 商品降级接口
 * @Version: 1.0
 */
//@FeignClient(value = "foodie-item-service",fallback = ItemCommentFallback.class)
@FeignClient(value = "foodie-item-service", fallbackFactory = ItemCommentsFallbackFactory.class)
public interface ItemCommentsFeignClient extends ItemCommentsService {
}

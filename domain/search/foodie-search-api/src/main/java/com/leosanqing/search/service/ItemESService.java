package com.leosanqing.search.service;


import com.leosanqing.pojo.PagedGridResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: leosanqing
 * @Date: 2020/3/4 下午5:53
 * @Package: com.leosanqing.service
 * @Description: 商品搜索服务接口
 */
@RequestMapping("search-api")
@FeignClient("foodie-search-service")
public interface ItemESService {
    @PostMapping("searchItems")
    PagedGridResult searchItems(@RequestParam String keywords,
                                @RequestParam String sort,
                                @RequestParam Integer page,
                                @RequestParam Integer pageSize);
}

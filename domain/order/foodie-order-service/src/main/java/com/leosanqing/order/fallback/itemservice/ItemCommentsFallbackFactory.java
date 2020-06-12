package com.leosanqing.order.fallback.itemservice;

import com.google.common.collect.Lists;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import com.leosanqing.pojo.PagedGridResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: rtliu
 * @Date: 2020/6/12 下午3:43
 * @Package: com.leosanqing.order.fallback.itemservice
 * @Description: 降级工厂类
 * @Version: 1.0
 */
@Component
public class ItemCommentsFallbackFactory implements FallbackFactory<ItemCommentsFeignClient> {
    @Override
    public ItemCommentsFeignClient create(Throwable throwable) {

        return new ItemCommentsFeignClient() {
            @Override
            public void saveComments(Map<String, Object> map) {

            }

            @Override
            public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
                MyCommentVO myCommentVO = new MyCommentVO();
                myCommentVO.setContent("正在加载中");

                PagedGridResult pagedGridResult = new PagedGridResult();
                pagedGridResult.setRows(Lists.newArrayList(myCommentVO));
                pagedGridResult.setPage(1);
                pagedGridResult.setTotal(1);
                return pagedGridResult;
            }
        };
    }
}

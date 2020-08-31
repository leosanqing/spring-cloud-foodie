package com.leosanqing.order.fallback.itemservice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author: rtliu
 * @Date: 2020/6/12 下午3:34
 * @Package: com.leosanqing.order.fallback.itemservice
 * @Description: 商品评论降级类
 * @Version: 1.0
 */
@RequestMapping("hh")
@Component
public class ItemCommentFallback implements ItemCommentsFeignClient {
    @Override
    public void saveComments(Map<String, Object> map) {
    }

    @Override
    public IPage<MyCommentVO> queryMyComments(String userId, Integer page, Integer pageSize) {
        MyCommentVO myCommentVO = new MyCommentVO();
        myCommentVO.setContent("正在加载中");

        Page<MyCommentVO> result = new Page<>();
        result.setRecords(Lists.newArrayList(myCommentVO));
        result.setCurrent(1);
        result.setTotal(1);

        return result;
    }
}

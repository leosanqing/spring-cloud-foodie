package com.leosanqing.order.fallback.itemservice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.leosanqing.item.pojo.vo.ItemCommentVO;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import com.leosanqing.pojo.PagedGridResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
        IPage<MyCommentVO> commentVOPage = new Page<>();

        MyCommentVO myCommentVO = new MyCommentVO();
        myCommentVO.setContent("正在加载中");

        commentVOPage.setRecords(Lists.newArrayList(myCommentVO));
        commentVOPage.setCurrent(page);
        commentVOPage.setTotal(1);
        commentVOPage.setSize(pageSize);
        commentVOPage.setPages(1);
        return commentVOPage;
    }
}

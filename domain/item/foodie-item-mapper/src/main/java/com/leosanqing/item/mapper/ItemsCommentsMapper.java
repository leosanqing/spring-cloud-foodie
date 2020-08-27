package com.leosanqing.item.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leosanqing.item.pojo.ItemsComments;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapper extends BaseMapper<ItemsComments> {

    /**
     * 保存评价列表
     *
     * @param map
     */
    void saveComments(Map<String, Object> map);

    /**
     * 查询我的评价
     *
     * @param userId
     * @param page
     * @return
     */
    IPage<MyCommentVO> queryMyComments(@Param("userId") String userId, Page page);
}
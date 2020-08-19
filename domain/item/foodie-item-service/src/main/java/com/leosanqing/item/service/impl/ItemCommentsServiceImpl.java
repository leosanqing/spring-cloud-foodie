package com.leosanqing.item.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.leosanqing.item.mapper.ItemsCommentsMapper;
import com.leosanqing.item.mapper.ItemsCommentsMapperCustom;
import com.leosanqing.item.pojo.ItemsComments;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import com.leosanqing.item.service.ItemCommentsService;
import com.leosanqing.pojo.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: rtliu
 * @Date: 2020/4/27 下午3:13
 * @Package: com.leosanqing.item.service.impl
 * @Description: 商品评价服务接口实现
 * @Version: 1.0
 */
@RestController
public class ItemCommentsServiceImpl
        extends ServiceImpl<ItemsCommentsMapper, ItemsComments>
        implements ItemCommentsService {

    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public IPage<MyCommentVO> queryMyComments(
            @RequestParam("userId") String userId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {

        return baseMapper.queryMyComments(userId, new Page(page, pageSize));

    }

    @Override
    public void saveComments(@RequestBody Map<String, Object> map) {
        itemsCommentsMapperCustom.saveComments(map);
    }
}

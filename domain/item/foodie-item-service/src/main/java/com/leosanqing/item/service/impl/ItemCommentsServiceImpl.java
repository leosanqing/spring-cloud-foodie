package com.leosanqing.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.leosanqing.item.mapper.ItemsCommentsMapperCustom;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import com.leosanqing.item.service.ItemCommentsService;
import com.leosanqing.pojo.PagedGridResult;
import com.leosanqing.service.BaseService;
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
public class ItemCommentsServiceImpl extends BaseService implements ItemCommentsService {
    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(@RequestParam("userId") String userId,
                                           @RequestParam(value = "page", required = false) Integer page,
                                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        return setterPage(list, page);
    }

    @Override
    public void saveComments(@RequestBody Map<String, Object> map) {
        itemsCommentsMapperCustom.saveComments(map);
    }
}

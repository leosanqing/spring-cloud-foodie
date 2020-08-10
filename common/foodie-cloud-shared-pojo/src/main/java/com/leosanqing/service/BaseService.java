package com.leosanqing.service;

import com.github.pagehelper.PageInfo;
import com.leosanqing.pojo.PagedGridResult;

import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2020/4/23 下午2:36
 * @Package: com.leosanqing.service
 * @Description: 基础服务类
 * @Version: 1.0
 */
public class BaseService {
    public PagedGridResult setterPage(List<?> list, int page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        return PagedGridResult.builder()
                .page(page)
                .rows(list)
                .total(pageList.getPages())
                .records(pageList.getTotal())
                .build();
    }
}

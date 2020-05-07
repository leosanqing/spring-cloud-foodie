package com.leosanqing.search.controller;


import com.leosanqing.pojo.JSONResult;
import com.leosanqing.search.service.ItemESService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: leosanqing
 * @Date: 2019-12-08 21:01
 */
@RestController
@RequestMapping("items")
@Api(value = "商品接口", tags = {"商品展示的相关接口"})
public class SearchController {

    final static Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ItemESService itemESService;

    @GetMapping("/es/search")
    public JSONResult searchItems(
            String keywords,
            String sort,
            Integer page,
            Integer pageSize) {

        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg("关键字为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        page--;
        return JSONResult.ok(itemESService.searchItems(keywords, sort, page, pageSize));
    }

    @GetMapping("leosanqing")
    public String hello() {

        logger.info("hello");
        return "hello";
    }


}

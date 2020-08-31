package com.leosanqing.item.controller;

import com.leosanqing.item.pojo.vo.ItemInfoVO;
import com.leosanqing.item.pojo.vo.ShopcartVO;
import com.leosanqing.item.service.ItemService;
import com.leosanqing.pojo.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/4/27 上午11:50
 * @Package: com.leosanqing.item.controller
 * @Description: 商品  Controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/items")
@Api(value = "商品接口", tags = {"商品展示的相关接口"})
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "商品详情", notes = "商品详情", httpMethod = "GET")
    public JSONResult subCats(
            @ApiParam(name = "itemId", value = "商品Id", required = true)
            @PathVariable String itemId
    ) {

        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品id为空");
        }

        ItemInfoVO itemInfoVO = ItemInfoVO
                .builder()
                .item(itemService.queryItemsById(itemId))
                .itemImgList(itemService.queryItemImgList(itemId))
                .itemSpecList(itemService.queryItemSpecList(itemId))
                .itemParams(itemService.queryItemParam(itemId))
                .build();
        return JSONResult.ok(itemInfoVO);
    }


    @GetMapping("commentLevel")
    @ApiOperation(value = "商品评价等级", notes = "商品评价等级", httpMethod = "GET")
    public JSONResult getCommentsCount(
            @ApiParam(name = "itemId", value = "商品Id", required = true)
            @RequestParam String itemId
    ) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品id为空");
        }
        return JSONResult.ok(itemService.queryCommentCounts(itemId));
    }

    @GetMapping("comments")
    @ApiOperation(value = "查询商品评价", notes = "查询商品评价", httpMethod = "GET")
    public JSONResult getCommentsCount(
            @ApiParam(name = "itemId", value = "商品Id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "商品等级", required = false)
            @RequestParam(required = false) Integer level,
            @ApiParam(name = "page", value = "第几页", required = false)
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @ApiParam(name = "pageSize", value = "每页个数", required = false)
            @RequestParam(defaultValue = "10", required = false) Integer pageSize
    ) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg("商品id为空");
        }
        return JSONResult.ok(itemService.queryPagedComments(itemId, level, page, pageSize));
    }


//    @GetMapping("search")
//    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
//    public JSONResult searchItems(
//            @ApiParam(name = "keywords", value = "关键字", required = true)
//            @RequestParam String keywords,
//            @ApiParam(name = "sort", value = "排序规则", required = false)
//            @RequestParam String sort,
//            @ApiParam(name = "page", value = "第几页", required = false)
//            @RequestParam(defaultValue = "1") Integer page,
//            @ApiParam(name = "pageSize", value = "每页个数", required = false)
//            @RequestParam(defaultValue = "10") Integer pageSize) {
//
//        if (StringUtils.isBlank(keywords)) {
//            return JSONResult.errorMsg("关键字为空");
//        }
//        return JSONResult.ok(itemService.searchItems(keywords,sort,page,pageSize));
//    }
//
//    @GetMapping("catItems")
//    @ApiOperation(value = "根据第三级分类搜索商品列表", notes = "根据第三级分类搜索商品列表", httpMethod = "GET")
//    public JSONResult searchItems(
//            @ApiParam(name = "catId", value = "第三级分类id", required = true)
//            @RequestParam Integer catId,
//            @ApiParam(name = "sort", value = "排序规则", required = false)
//            @RequestParam String sort,
//            @ApiParam(name = "page", value = "第几页", required = false)
//            @RequestParam(defaultValue = "1") Integer page,
//            @ApiParam(name = "pageSize", value = "每页个数", required = false)
//            @RequestParam(defaultValue = "10") Integer pageSize) {
//
//        if (catId == null) {
//            return JSONResult.errorMsg("分类为空");
//        }
//        return JSONResult.ok(itemService.searchItemsByCatId(catId,sort,page,pageSize));
//    }

    @GetMapping("refresh")
    @ApiOperation(value = "刷新购物车", notes = "刷新购物车", httpMethod = "GET")
    public JSONResult queryItemsBySpecIds(
            @ApiParam(name = "itemSpecIds", value = "商品规格Id列表", required = true)
            @RequestParam String itemSpecIds
    ) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.errorMsg("商品规格Id列表为空");
        }

        return JSONResult.ok(itemService.queryItemsBySpecIds(itemSpecIds));
    }
}

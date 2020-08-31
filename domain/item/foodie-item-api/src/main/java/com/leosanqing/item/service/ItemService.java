package com.leosanqing.item.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leosanqing.item.pojo.Items;
import com.leosanqing.item.pojo.ItemsImg;
import com.leosanqing.item.pojo.ItemsParam;
import com.leosanqing.item.pojo.ItemsSpec;
import com.leosanqing.item.pojo.vo.CommentLevelCountsVO;
import com.leosanqing.item.pojo.vo.ItemCommentVO;
import com.leosanqing.item.pojo.vo.ShopcartVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2019-12-08 20:44
 */

@RequestMapping("item-api")
@FeignClient("foodie-item-service")
public interface ItemService {

    /**
     * 根据商品id查询商品
     *
     * @param itemId
     * @return
     */
    @GetMapping("item")
    Items queryItemsById(@RequestParam("itemId") String itemId);

    /**
     * 根据商品id查询商品图片
     *
     * @param itemId
     * @return
     */
    @GetMapping("itemImages")
    List<ItemsImg> queryItemImgList(@RequestParam("itemId") String itemId);

    /**
     * 根据商品id查询商品规格
     *
     * @param itemId
     * @return
     */
    @GetMapping("itemSpecs")
    List<ItemsSpec> queryItemSpecList(@RequestParam("itemId") String itemId);

    /**
     * 根据商品Id查询商品参数
     *
     * @param itemId
     * @return
     */
    @GetMapping("itemParam")
    ItemsParam queryItemParam(@RequestParam("itemId") String itemId);


    /**
     * 查询商品的各个评价等级
     *
     * @param itemId
     * @return
     */
    @GetMapping("countComments")
    CommentLevelCountsVO queryCommentCounts(@RequestParam("itemId") String itemId);


    /**
     * 根据商品id查询商品评价
     *
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("pagedComments")
    IPage<ItemCommentVO> queryPagedComments(
            @RequestParam("itemId") String itemId,
            @RequestParam(value = "level", required = false) Integer level,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    );

    /**
     * 根据关键字查询商品
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
//    PagedGridResult searchItems(String keywords,String sort,Integer page,Integer pageSize);

    /**
     * 根据第三级目录查询商品
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
//    PagedGridResult searchItemsByCatId(Integer catId,String sort,Integer page,Integer pageSize);

    /**
     * 根据商品规格id查询商品信息
     *
     * @param specIds
     * @return
     */
    @GetMapping("getCartBySpecIds")
    List<ShopcartVO> queryItemsBySpecIds(@RequestParam("specIds") String specIds);

    /**
     * 根据商品id 查询商品规格
     *
     * @param specId
     * @return
     */
    @GetMapping("singleItemSpec")
    ItemsSpec queryItemBySpecId(@RequestParam("specId") String specId);


    /**
     * 根据商品id ，查询商品图片
     *
     * @param itemId
     * @return
     */
    @GetMapping("primaryImage")
    String queryItemImgByItemId(@RequestParam("itemId") String itemId);

    /**
     * 减库存
     *
     * @param specId
     * @param buyCounts
     */
    @PostMapping("decreaseStock")
    void decreaseItemSpecStock(@RequestParam("specId") String specId, @RequestParam("buyCounts") Integer buyCounts);
}

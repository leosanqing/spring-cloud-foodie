package com.leosanqing.test.item.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leosanqing.item.pojo.Items;
import com.leosanqing.item.pojo.ItemsImg;
import com.leosanqing.item.pojo.ItemsParam;
import com.leosanqing.item.pojo.ItemsSpec;
import com.leosanqing.item.pojo.vo.CommentLevelCountsVO;
import com.leosanqing.item.pojo.vo.ItemCommentVO;
import com.leosanqing.item.pojo.vo.ShopcartVO;
import com.leosanqing.item.service.ItemService;
import com.leosanqing.test.item.BaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/8/26 上午9:54
 * @Package: com.leosanqing.test.item.controller
 * @Description: ItemController 测试
 * @Version: 1.0
 */

public class ItemServiceTest extends BaseTest {
    @Autowired
    ObjectMapper mapper;

    @Autowired
    ItemService itemService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        itemService = (ItemService) AopProxyUtils.getSingletonTarget(itemService);
        MockitoAnnotations.initMocks(this);
    }

    private final static String ITEM_ID = "cake-1001";


    @Test
    void shouldGetItemInfo() {
        Items items = itemService.queryItemsById(ITEM_ID);
        Assert.assertEquals(37, (int) items.getCatId());
    }

    @Test
    void queryItemImgList() {
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(ITEM_ID);
        Assert.assertEquals(3, itemsImgs.size());
        Assert.assertEquals(
                "http://122.152.205.72:88/foodie/cake-1001/img1.png",
                itemsImgs.get(0).getUrl()
        );
    }


    @Test
    void queryItemSpecList() {
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(ITEM_ID);
        Assert.assertEquals(3, itemsSpecs.size());
        Assert.assertEquals("原味", itemsSpecs.get(0).getName());
    }


    @Test
    void queryItemParam() {
        ItemsParam itemsParam = itemService.queryItemParam(ITEM_ID);
        Assert.assertEquals("北京中关村", itemsParam.getFactoryAddress());
        Assert.assertEquals("1", itemsParam.getId());
    }


    @Test
    void queryCommentCounts() {
        CommentLevelCountsVO levelCountsVO = itemService.queryCommentCounts(ITEM_ID);
        Assert.assertEquals(2, (int) levelCountsVO.getBadCounts());
        Assert.assertEquals(14, (int) levelCountsVO.getGoodCounts());
        Assert.assertEquals(7, (int) levelCountsVO.getNormalCounts());
        Assert.assertEquals(23, (int) levelCountsVO.getTotalCounts());
    }


    @Test
    void queryPagedComments() {
        IPage<ItemCommentVO> comments = itemService.queryPagedComments(ITEM_ID, 1, 1, 10);
        Assert.assertEquals(14, comments.getTotal());
        Assert.assertEquals(10, comments.getRecords().size());

        Assert.assertEquals(1, (int) comments.getRecords().get(0).getCommentLevel());
        Assert.assertEquals("很棒", comments.getRecords().get(0).getContent());
    }


    @Test
    void decreaseItemSpecStock() {
        Integer stock = itemService.queryItemBySpecId("1").getStock();
        itemService.decreaseItemSpecStock("1", 1);

        Assert.assertEquals(
                (int) itemService.queryItemBySpecId("1").getStock(),
                stock - 1
        );
    }

    @Test
    void queryItemsBySpecIds() {
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds("1,2");
        Assert.assertEquals(2, shopcartVOS.size());
        Assert.assertEquals(ITEM_ID, shopcartVOS.get(0).getItemId());
    }

    @Test
    void queryItemBySpecId() {
        ItemsSpec itemsSpec = itemService.queryItemBySpecId("1");
        Assert.assertEquals("0.90", itemsSpec.getDiscounts().toPlainString());
        Assert.assertEquals(ITEM_ID, itemsSpec.getItemId());
    }

    @Test
    void queryItemImgByItemId() {
        String imgUrl = itemService.queryItemImgByItemId(ITEM_ID);

        Assert.assertEquals(
                "http://122.152.205.72:88/foodie/cake-1001/img1.png",
                imgUrl
        );

    }
}

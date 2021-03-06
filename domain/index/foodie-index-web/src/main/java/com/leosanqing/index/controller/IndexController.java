package com.leosanqing.index.controller;

import com.leosanqing.enums.YesOrNo;
import com.leosanqing.index.pojo.Carousel;
import com.leosanqing.index.pojo.Category;
import com.leosanqing.index.pojo.vo.CategoryVO;
import com.leosanqing.index.pojo.vo.NewItemsVO;
import com.leosanqing.index.service.CarouselService;
import com.leosanqing.index.service.CategoryService;
import com.leosanqing.utils.JsonUtils;
import com.leosanqing.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2019-12-07 22:42
 */
@RestController
@RequestMapping("index")
@Api(value = "首页", tags = {"首页展示的相关接口"})
@Slf4j
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisOperator redisOperator;


    @GetMapping("carousel")
    @ApiOperation(value = "获取首页了轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    public List<Carousel> carousel() {

        /*
         * 轮播图失效时间：
         *  1. 由后台运行系统统一重置，然后删除缓存
         *  2. 定时重置，比如每天夜里
         *  3. 设置超时时间，时间过了重置
         */
        List<Carousel> carousels;
        final String carouselStr = redisOperator.get("carousel");
        if (!StringUtils.isBlank(carouselStr)) {
            return JsonUtils.jsonToList(carouselStr, Carousel.class);
        }

        carousels = carouselService.queryAll(YesOrNo.YES.type);
        redisOperator.set("carousel", JsonUtils.objectToJson(carousels));

        return carousels;
    }

    @GetMapping("cats")
    @ApiOperation(value = "获取一级目录所有节点", notes = "获取一级目录所有节点", httpMethod = "GET")
    public List<Category> cats() {

        List<Category> categoryList;

        final String catsStr = redisOperator.get("cats");
        if (!StringUtils.isBlank(catsStr)) {
            return JsonUtils.jsonToList(catsStr, Category.class);
        }
        categoryList = categoryService.queryAllRootLevelCat();
        redisOperator.set("cats", JsonUtils.objectToJson(categoryList));
        return categoryList;
    }

    @GetMapping("subCat/{rootCatId}")
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    public List<CategoryVO> subCats(
            @ApiParam(name = "rootCatId", value = "一级分类Id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            throw new RuntimeException("商品分类不存在");
        }

        List<CategoryVO> categoryVOList;
        final String subCatStr = redisOperator.get("subCat:" + rootCatId);
        if (!StringUtils.isBlank(subCatStr)) {
            return JsonUtils.jsonToList(subCatStr, CategoryVO.class);
        }

        categoryVOList = categoryService.getSubCatList(rootCatId);
        if (categoryVOList == null || categoryVOList.size() == 0) {
            redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(categoryVOList));
        } else {
            redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(categoryVOList), 5 * 60 * 1000);
        }
        return categoryVOList;
    }


    @GetMapping("sixNewItems/{rootCatId}")
    @ApiOperation(value = "查询每个分类下的六个最新商品", notes = "查询每个分类下的六个最新商品", httpMethod = "GET")
    public List<NewItemsVO> getSixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类Id", required = true)
            @PathVariable Integer rootCatId
    ) {
        if (rootCatId == null) {
            throw new RuntimeException("商品分类不存在");
        }

        return categoryService.getSixNewItemsLazy(rootCatId);
    }


}

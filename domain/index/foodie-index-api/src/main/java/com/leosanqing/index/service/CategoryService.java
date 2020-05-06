package com.leosanqing.index.service;

import com.leosanqing.index.pojo.Category;
import com.leosanqing.index.pojo.vo.CategoryVO;
import com.leosanqing.index.pojo.vo.NewItemsVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/5/6 上午11:05
 * @Package: com.leosanqing.index.service
 * @Description: 商品分类服务
 * @Version: 1.0
 */
@RequestMapping("category-api")

public interface CategoryService {

    /**
     * 查询一级分类下的所有节点
     *
     * @return
     */
    @PostMapping("queryAllRootLevelCat")
    List<Category> queryAllRootLevelCat();

    /**
     * 查询子分类信息
     *
     * @param rootCatId
     * @return
     */
    @PostMapping("querySubCatList")
    List<CategoryVO> getSubCatList(Integer rootCatId);


    /**
     * 查询每个分类下的六个商品信息
     *
     * @param rootCatId
     * @return
     */
    @PostMapping("querySixNewItemsLazy")
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}

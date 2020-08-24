//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.leosanqing.index.mapper;

import com.leosanqing.index.pojo.vo.CategoryVO;
import com.leosanqing.index.pojo.vo.NewItemsVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CategoryMapperCustom {
    /**
     * 查询子分类
     *
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询每个分类下的六个商品信息
     *
     * @param map
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(@Param("paramMap") Map<String, Object> map);
}

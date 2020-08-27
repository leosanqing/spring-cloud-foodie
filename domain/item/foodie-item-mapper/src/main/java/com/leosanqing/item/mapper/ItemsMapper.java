package com.leosanqing.item.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leosanqing.item.pojo.Items;
import com.leosanqing.item.pojo.vo.ItemCommentVO;
import com.leosanqing.item.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapper extends BaseMapper<Items> {

    /**
     * 查询商品评价
     *
     * @param map
     * @return
     */
    IPage<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map, @Param("page") Page page);

    /**
     * 根据关键字查询商品
     *
     * @param map
     * @return
     */
//    List<SearchItemsVO> searchItems(
//            @Param("paramsMap") Map<String, String> map);

    /**
     * 根据第三级目录查询商品
     *
     * @param map
     * @return
     */
//    List<SearchItemsVO> searchItemsByThirdCatId(
//            @Param("paramsMap") Map<String, Object> map);


    /**
     * 根据第三级目录查询商品
     *
     * @param specIdsList
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List<String> specIdsList);

    /**
     * 减库存
     *
     * @param pendingCount
     * @param specId
     * @return
     */
    Integer decreaseItemSpecStock(@Param("itemSpecId") String specId, @Param("pendingCount") Integer pendingCount);


}
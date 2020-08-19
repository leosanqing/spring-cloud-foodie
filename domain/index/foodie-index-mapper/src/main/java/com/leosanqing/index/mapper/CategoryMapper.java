package com.leosanqing.index.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leosanqing.index.pojo.Category;
import com.leosanqing.index.pojo.vo.CategoryVO;
import com.leosanqing.index.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {

    static String getSubCatListSql(Integer rootCatId) {
        return new SQL() {{

            SELECT(
                    "f.id as id,\n" +
                            "f.`name` as `name`,\n" +
                            "f.type as type,\n" +
                            "f.father_id as fatherId,\n" +
                            "c.id as subId,\n" +
                            "c.`name` as subName,\n" +
                            "c.type as subType,\n" +
                            "c.father_id as subFatherId"
            );

            FROM(" category f ");
            INNER_JOIN("category c ON f.id = c.father_id");

            WHERE(
                    "f.id = c.father_id\n" +
                            "    where f.father_id = #{rootCatId}"
            );

        }}.toString();
    }

    @SelectProvider(type = CategoryMapper.class, method = "getSubCatListSql")
    List<CategoryVO> getSubCatList(@Param("rootCatId") Integer rootCatId);


    static String getSixNewItemsLazySql(Integer rootCatId) {
        return new SQL() {{

            SELECT(
                    " f.id as rootCatId,\n" +
                            " f.`name` as rootCatName,\n" +
                            " f.slogan as slogan,\n" +
                            " f.cat_image as catImage,\n" +
                            " f.bg_color as bgColor,\n" +
                            " i.id as itemId,\n" +
                            " i.item_name as itemName,\n" +
                            " ii.url as itemUrl,\n" +
                            " i.created_time as createdTime"
            );

            FROM(" category f ");
            INNER_JOIN(
                    "items i ON f.id = i.root_cat_id",
                    "items_img ii ON i.id = ii.item_id"
            );

            WHERE(
                    "f.type = 1\n" +
                            "and i.root_cat_id = #{paramMap.rootCatId}\n" +
                            "and ii.is_main = 1"
            );

            ORDER_BY("i.created_time DESC limit(0,6)");


        }}.toString();
    }

    @SelectProvider(type = CategoryMapper.class, method = "getSixNewItemsLazySql")
    List<NewItemsVO> getSixNewItemsLazy(@Param("rootCatId") Integer rootCatId);

}
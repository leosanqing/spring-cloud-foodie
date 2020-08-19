package com.leosanqing.item.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leosanqing.item.pojo.Items;
import com.leosanqing.item.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.Objects;

public interface ItemsMapper extends BaseMapper<Items> {


    static String queryItemCommentsSql(String itemId, Integer level, Page page) {
        return new SQL() {{
            SELECT(
                    "ic.comment_level as commentLevel,\n" +
                            "       ic.content as content,\n" +
                            "       ic.sepc_name as sepcName,\n" +
                            "       ic.created_time as createdTime,\n" +
                            "       u.face as face,\n" +
                            "       u.nickname as nickname"
            );

            FROM(" items_comments ic ");
            INNER_JOIN("users u ON ic.user_id = u.id");

            WHERE("ic.item_id = #{itemId}");
            if (Objects.nonNull(level)) {
                WHERE("ic.comment_level =#{level}");
            }

        }}.toString();
    }

    /**
     * 查询商品评价
     *
     * @param itemId
     * @param level
     * @param page
     * @return
     */
    @SelectProvider(type = ItemsMapper.class, method = "queryItemCommentsSql")
    IPage<ItemCommentVO> queryItemComments(
            @Param("itemId") String itemId,
            @Param("level") Integer level,
            @Param("page") Page page
    );
}
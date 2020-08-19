package com.leosanqing.item.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leosanqing.item.pojo.ItemsComments;
import com.leosanqing.item.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public interface ItemsCommentsMapper extends BaseMapper<ItemsComments> {

    static String queryItemCommentsSql(String userId, Page page) {
        return new SQL() {{
            SELECT("ic.id as commentId,\n" +
                    "    ic.content as content,\n" +
                    "    ic.created_time as createdTime,\n" +
                    "    ic.item_id as itemId,\n" +
                    "    ic.item_name as itemName,\n" +
                    "    ic.sepc_name as specName,\n" +
                    "    ii.url as itemImg");

            FROM(" items_comments ic ");
            INNER_JOIN("items_img ii ON ic.item_id = ii.item_id");

            WHERE(" ic.user_id = #{userId} \n" +
                    "AND ii.is_main = 1");

            ORDER_BY("ic.created_time DESC");
        }}.toString();
    }

    /**
     * 查询我的评价
     *
     * @param userId
     * @param page
     * @return
     */
    IPage<MyCommentVO> queryMyComments(@Param("userId") String userId, @Param("page") Page page);
}
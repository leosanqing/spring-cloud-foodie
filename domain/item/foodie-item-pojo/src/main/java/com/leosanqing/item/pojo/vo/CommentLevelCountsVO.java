package com.leosanqing.item.pojo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: leosanqing
 * @Date: 2019-12-08 21:37
 * <p>
 * 商品 评价VO
 */
@Data
@Builder
public class CommentLevelCountsVO {

    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;
}

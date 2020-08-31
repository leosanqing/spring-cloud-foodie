package com.leosanqing.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: leosanqing
 * @Date: 2019-12-12 08:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopCartBO {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;

}

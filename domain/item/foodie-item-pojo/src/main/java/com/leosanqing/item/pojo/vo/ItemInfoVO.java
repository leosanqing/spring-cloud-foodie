package com.leosanqing.item.pojo.vo;


import com.leosanqing.item.pojo.Items;
import com.leosanqing.item.pojo.ItemsImg;
import com.leosanqing.item.pojo.ItemsParam;
import com.leosanqing.item.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2019-12-08 21:10
 * <p>
 * 商品详情VO
 */
@Data
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;
}

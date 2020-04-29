package com.leosanqing.order.pojo.bo;

import com.leosanqing.pojo.ShopCartBO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/4/27 下午3:04
 * @Package: com.leosanqing.order.pojo.bo
 * @Description: 订单相关信息
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class PlaceOrderBO {
    private SubmitOrderBO order;

    private List<ShopCartBO> items;
}

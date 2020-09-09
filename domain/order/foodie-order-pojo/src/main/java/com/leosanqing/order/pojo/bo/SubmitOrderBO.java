package com.leosanqing.order.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: leosanqing
 * @Date: 2019-12-14 15:05
 * @Package: com.leosanqing.pojo.bo
 * @Description: 提交订单对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmitOrderBO {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}

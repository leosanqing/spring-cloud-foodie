package com.leosanqing.cart.service;

import com.leosanqing.pojo.ShopCartBO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: rtliu
 * @Date: 2020/4/26 下午12:00
 * @Package: com.leosanqing.cart.service
 * @Description: 购物车服务接口
 * @Version: 1.0
 */
@RequestMapping("cart-api")
public interface CartService {

    @PostMapping("addItem")
    boolean addItemToCart(@RequestParam("userId") String userId,
                          @RequestBody ShopCartBO shopcartBO);

    @PostMapping("removeItem")
    boolean removeItemFromCart(@RequestParam("userId") String userId,
                               @RequestParam("itemSpecId") String itemSpecId);

    @PostMapping("clearCart")
    boolean clearCart(@RequestParam("userId") String userId);

}

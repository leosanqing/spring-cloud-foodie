package com.leosanqing.cart.service.impl;

import com.leosanqing.cart.service.CartService;
import com.leosanqing.pojo.ShopCartBO;
import com.leosanqing.utils.JsonUtils;
import com.leosanqing.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.leosanqing.controller.BaseController.SHOP_CART;

/**
 * @Author: rtliu
 * @Date: 2020/4/27 上午9:35
 * @Package: PACKAGE_NAME
 * @Description: 购物车服务实现
 * @Version: 1.0
 */

@Slf4j
@RestController
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisOperator redisOperator;


    @Override
    public boolean addItemToCart(@RequestParam("userId") String userId,
                                 @RequestBody ShopCartBO ShopCartBO) {
        // 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        // 需要判断当前购物车中包含已经存在的商品，如果存在则累加购买数量
        String shopcartJson = redisOperator.get(SHOP_CART + ":" + userId);
        List<ShopCartBO> shopcartList = null;
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis中已经有购物车了
            shopcartList = JsonUtils.jsonToList(shopcartJson, ShopCartBO.class);
            // 判断购物车中是否存在已有商品，如果有的话counts累加
            boolean isHaving = false;
            for (ShopCartBO sc : shopcartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(ShopCartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + ShopCartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopcartList.add(ShopCartBO);
            }
        } else {
            // redis中没有购物车
            shopcartList = new ArrayList<>();
            // 直接添加到购物车中
            shopcartList.add(ShopCartBO);
        }

        // 覆盖现有redis中的购物车
        redisOperator.set(SHOP_CART + ":" + userId, JsonUtils.objectToJson(shopcartList));

        return true;
    }

    @Override
    public boolean removeItemFromCart(@RequestParam("userId") String userId,
                                      @RequestParam("itemSpecId") String itemSpecId) {
        // 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除redis购物车中的商品
        String shopcartJson = redisOperator.get(SHOP_CART + ":" + userId);
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis中已经有购物车了
            List<ShopCartBO> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopCartBO.class);
            // 判断购物车中是否存在已有商品，如果有的话则删除
            for (ShopCartBO sc : shopcartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(itemSpecId)) {
                    shopcartList.remove(sc);
                    break;
                }
            }
            // 覆盖现有redis中的购物车
            redisOperator.set(SHOP_CART + ":" + userId, JsonUtils.objectToJson(shopcartList));
        }

        return true;
    }

    @Override
    public boolean clearCart(@RequestParam("userId") String userId) {
        redisOperator.del(SHOP_CART + ":" + userId);
        return true;
    }
}

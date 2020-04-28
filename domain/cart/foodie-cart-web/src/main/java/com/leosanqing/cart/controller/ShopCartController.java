package com.leosanqing.cart.controller;

import com.leosanqing.cart.service.CartService;
import com.leosanqing.pojo.JSONResult;
import com.leosanqing.pojo.ShopCartBO;
import com.leosanqing.utils.JsonUtils;
import com.leosanqing.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.leosanqing.controller.BaseController.SHOP_CART;

/**
 * @Author: rtliu
 * @Date: 2020/4/27 上午9:50
 * @Package: com.leosanqing.cart.controller
 * @Description: 购物车 controller 层
 * @Version: 1.0
 */
@RestController
@RequestMapping("shopcart")
@Api(value = "购物车相关接口api", tags = {"用于购物车相关操作"})
public class ShopCartController {
    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private CartService cartService;

    @PostMapping("add")
    @ApiOperation(value = "添加购物车", notes = "添加购物车", httpMethod = "POST")
    public JSONResult add(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "shopCartBO", value = "从前端传来的购物车对象")
            @RequestBody ShopCartBO shopCartBO
//            HttpServletRequest request,
//            HttpServletResponse response
    ) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }

        System.out.println(shopCartBO);
        cartService.addItemToCart(userId, shopCartBO);

        return JSONResult.ok();
    }


    @PostMapping("del")
    @ApiOperation(value = "删除购物车", notes = "删除购物车", httpMethod = "POST")
    public JSONResult del(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "itemSpecId", value = "购物车中的商品规格")
            @RequestBody String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("参数不能为空");
        }
        cartService.removeItemFromCart(userId, itemSpecId);

        return JSONResult.ok();
    }

    @PostMapping("clear_cart")
    @ApiOperation(value = "清空购物车", notes = "清空购物车", httpMethod = "POST")
    public JSONResult clearCart(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId
    ) {
        cartService.clearCart(userId);

        return JSONResult.ok();
    }
}

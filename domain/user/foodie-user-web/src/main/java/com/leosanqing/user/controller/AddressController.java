package com.leosanqing.user.controller;

import com.leosanqing.user.pojo.UserAddress;
import com.leosanqing.user.pojo.bo.AddressBO;
import com.leosanqing.user.service.AddressService;
import com.leosanqing.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2019-12-12 07:59
 */
@RestController
@RequestMapping("address")
@Api(value = "地址相关接口api", tags = {"查询地址相关"})
@Slf4j
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("list")
    @ApiOperation(value = "查询所有收货地址", notes = "查询所有收货地址", httpMethod = "POST")
    public List<UserAddress> queryAll(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId
    ) {
        if (StringUtils.isBlank(userId)) {
            throw new RuntimeException("用户名id为空");
        }

        return addressService.queryAll(userId);
    }


    @PostMapping("add")
    @ApiOperation(value = "添加收货地址", notes = "添加收货地址", httpMethod = "POST")
    public void add(
            @ApiParam(name = "addressBO", value = "收货地址BO")
            @Validated @RequestBody AddressBO addressBO
    ) {
        if (addressBO == null) {
            throw new RuntimeException("传入地址对象为空");
        }

        addressService.addNewUserAddress(addressBO);
    }


    @PostMapping("update")
    @ApiOperation(value = "添加收货地址", notes = "添加收货地址", httpMethod = "POST")
    public void update(
            @ApiParam(name = "addressBO", value = "收货地址BO")
            @RequestBody AddressBO addressBO
    ) {
        if (addressBO == null) {
            throw new RuntimeException("传入地址对象为空");
        }
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            throw new RuntimeException("修改地址错误: 地址ID不能为空");
        }

        addressService.updateUserAddress(addressBO);
    }


    @PostMapping("delete")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", httpMethod = "POST")
    public void del(
            @ApiParam(name = "userId", value = "用户Id")
            @RequestParam String userId,
            @ApiParam(name = "addressId", value = "收货地址Id")
            @RequestParam String addressId
    ) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            throw new RuntimeException("收货地址id 或 用户id 为空");
        }

        addressService.deleteUserAddress(userId, addressId);
    }


    @PostMapping("setDefault")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", httpMethod = "POST")
    public void setDefault(
            @ApiParam(name = "userId", value = "用户Id")
            @RequestParam String userId,
            @ApiParam(name = "addressId", value = "收货地址Id")
            @RequestParam String addressId
    ) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            throw new RuntimeException("收货地址id 或 用户id 为空");
        }

        addressService.updateToBeDefault(userId, addressId);
    }


}

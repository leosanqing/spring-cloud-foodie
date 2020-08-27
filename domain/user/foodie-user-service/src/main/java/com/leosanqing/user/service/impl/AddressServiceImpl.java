package com.leosanqing.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leosanqing.enums.YesOrNo;
import com.leosanqing.user.mapper.UserAddressMapper;
import com.leosanqing.user.pojo.UserAddress;
import com.leosanqing.user.pojo.bo.AddressBO;
import com.leosanqing.user.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2019-12-13 22:30
 * @Package: com.leosanqing.service.impl
 * @Description: 收货地址相关服务
 */
@RestController
public class AddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements AddressService {

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        return lambdaQuery()
                .eq(UserAddress::getUserId, userId)
                .list();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewUserAddress(AddressBO addressBO) {

        int isDefault = 0;
        // 查询之前是否存在地址
        List<UserAddress> addressList = queryAll(addressBO.getUserId());
        if (null == addressList || addressList.isEmpty()) {
            isDefault = 1;
        }

        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(sid.nextShort());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        baseMapper.insert(userAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAddress(AddressBO addressBO) {

        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);

        String addressId = addressBO.getAddressId();
        userAddress.setId(addressId);
        userAddress.setUpdatedTime(new Date());

        // 这样空值不会覆盖数据库中已有的数据
        baseMapper.updateById(userAddress);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserAddress(String userId, String addressId) {
        lambdaUpdate()
                .eq(UserAddress::getId, addressId)
                .eq(UserAddress::getUserId, userId)
                .remove();
    }

    @Override
    public void updateToBeDefault(String userId, String addressId) {

        // 将原来的地址修改为非默认地址
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setIsDefault(YesOrNo.YES.type);

        List<UserAddress> select = lambdaQuery()
                .eq(UserAddress::getUserId, userId)
                .eq(UserAddress::getIsDefault, YesOrNo.YES.type)
                .list();

        for (UserAddress address : select) {
            address.setIsDefault(YesOrNo.NO.type);
            baseMapper.updateById(address);
        }

        // 将现在的地址改为默认地址
        baseMapper.updateById(
                UserAddress
                        .builder()
                        .id(addressId)
                        .userId(userId)
                        .isDefault(YesOrNo.YES.type)
                        .build()
        );
    }

    @Override
    public UserAddress queryAddress(String userId, String addressId) {
        return lambdaQuery()
                .eq(UserAddress::getUserId, userId)
                .eq(UserAddress::getId, addressId)
                .one();
    }
}

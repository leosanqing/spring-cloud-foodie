package com.leosanqing.order.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leosanqing.enums.YesOrNo;
import com.leosanqing.item.pojo.ItemsSpec;
import com.leosanqing.item.service.ItemService;
import com.leosanqing.order.mapper.OrderItemsMapper;
import com.leosanqing.order.mapper.OrderStatusMapper;
import com.leosanqing.order.mapper.OrdersMapper;
import com.leosanqing.order.pojo.OrderItems;
import com.leosanqing.order.pojo.OrderStatus;
import com.leosanqing.order.pojo.Orders;
import com.leosanqing.order.pojo.bo.PlaceOrderBO;
import com.leosanqing.order.pojo.bo.SubmitOrderBO;
import com.leosanqing.order.pojo.vo.OrderVO;
import com.leosanqing.order.service.OrderService;
import com.leosanqing.pojo.ShopCartBO;
import com.leosanqing.user.pojo.UserAddress;
import com.leosanqing.user.service.AddressService;
import com.leosanqing.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2019-12-15 12:31
 * @Package: com.leosanqing.service.impl
 * @Description: TODO
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrderService {

    @Autowired
    private ItemService itemService;
    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

//    @Autowired
//    private RestTemplate restTemplate;
//    @Autowired
//    private LoadBalancerClient client;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectById(orderId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void closeOrder() {

        // 查询所有未付款订单，判断时间是否超时（1天），超时则关闭交易
        List<OrderStatus> list = orderStatusMapper.selectList(
                Wrappers
                        .lambdaQuery(OrderStatus.class)
                        .eq(OrderStatus::getOrderStatus, OrderStatus.OrderStatusEnum.WAIT_PAY.type)
        );

        list.forEach(
                os -> {
                    Date createdTime = os.getCreatedTime();
                    // 和当前时间进行对比
                    int days = DateUtil.daysBetween(createdTime, new Date());
                    if (days >= 1) {
                        // 超过1天，关闭订单
                        doCloseOrder(os.getOrderId());
                    }
                }
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void doCloseOrder(String orderId) {
        OrderStatus close = OrderStatus
                .builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.OrderStatusEnum.CLOSE.type)
                .closeTime(new Date())
                .build();
        orderStatusMapper.updateById(close);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(PlaceOrderBO placeOrderBO) {
        List<ShopCartBO> shopCartBOList = placeOrderBO.getItems();
        SubmitOrderBO submitOrderBO = placeOrderBO.getOrder();
        String userId = submitOrderBO.getUserId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        String addressId = submitOrderBO.getAddressId();
        String leftMsg = submitOrderBO.getLeftMsg();
        Integer payMethod = submitOrderBO.getPayMethod();

        Integer postAmount = 0;

        // 1.生成 新订单 ，填写 Orders表
        final String orderId = sid.nextShort();

        // feign简化
        UserAddress userAddress = addressService.queryAddress(userId, addressId);
//        ServiceInstance instance = client.choose("FOODIE-USER-SERVICE");
//        String url = String.format("http://%s:%s/address-api/queryAddress" +
//                        "?userId=%s&addressId=%s",
//                instance.getHost(),
//                instance.getPort(),
//                userId, addressId);
//        ResponseEntity<UserAddress> forEntity = restTemplate.getForEntity(url, UserAddress.class);
//        UserAddress userAddress = restTemplate.getForObject(url, UserAddress.class);

        Orders orders = Orders.builder()
                .id(orderId)
                .userId(userId)
                .leftMsg(leftMsg)
                .payMethod(payMethod)
                .receiverAddress(
                        userAddress.getProvince() + " " +
                                userAddress.getCity() + " " +
                                userAddress.getDistrict() + " " +
                                userAddress.getDetail()
                )
                .receiverMobile(userAddress.getMobile())
                .receiverName(userAddress.getReceiver())
                .postAmount(postAmount)
                .isComment(YesOrNo.NO.type)
                .isDelete(YesOrNo.NO.type)
                .createdTime(new Date())
                .updatedTime(new Date())
                .totalAmount(0)
                .totalAmount(0)
                .build();

        /*
            分库分表：orderItems作为orders的子表，所有插入时，要先插入Orders，
            这样在插入OrderItems时，才能找到对应的分片。所以这里先插入Orders,
            计算金额后，再更新一下Orders.
         */
        orders.setTotalAmount(0);
        orders.setRealPayAmount(0);
        baseMapper.insert(orders);


        // 2.1 循环根据商品规格表，保存到商品规格表
        int totalAmount = 0;
        int realPayTotalAmount = 0;
        String[] itemSpecIdArray = StringUtils.split(itemSpecIds, ',');
        List<ShopCartBO> toBeRemovedList = new ArrayList<>();
        for (String itemSpecId : itemSpecIdArray) {

            // 查询每个商品的规格
            ItemsSpec itemsSpec = itemService.queryItemBySpecId(itemSpecId);
            ShopCartBO shopCartBO = getShopCartBOFromList(shopCartBOList, itemSpecId);

            toBeRemovedList.add(shopCartBO);
            int counts = 0;
            if (shopCartBO != null) {
                counts = shopCartBO.getBuyCounts();
            }
            // 获取价格
//            ServiceInstance itemApi = client.choose("FOODIE-ITEM-SERVICE");
//            url = String.format("http://%s:%s/item-api/singleItemSpec?specId=%s",
//                    itemApi.getHost(),
//                    itemApi.getPort(),
//                    itemSpecId);
//            ItemsSpec itemsSpec = restTemplate.getForObject(url, ItemsSpec.class);
            totalAmount += itemsSpec.getPriceNormal() * counts;
            realPayTotalAmount += itemsSpec.getPriceDiscount() * counts;

            // 2.2 根据商品id，获得商品图片和信息
            String itemId = itemsSpec.getItemId();

            //  等待feign章节再来简化
            //  作业 -同学们自己改造
            String imgUrl = itemService.queryItemImgByItemId(itemId);
//            String imgUrl = null;

            // 2.3 将商品规格信息写入 订单商品表
            OrderItems subOrderItem = OrderItems.builder()
                    .buyCounts(counts)
                    .id(sid.nextShort())
                    .itemId(itemId)
                    .itemImg(imgUrl)
                    .itemName(itemsSpec.getName())
                    .itemSpecId(itemSpecId)
                    .orderId(orderId)
                    .itemSpecName(itemsSpec.getName())
                    .price(itemsSpec.getPriceDiscount())
                    .build();
            orderItemsMapper.insert(subOrderItem);


            // 2.4 减库存
            itemService.decreaseItemSpecStock(itemSpecId, counts);

            // 2.4 在用户提交订单以后，规格表中需要扣除库存
//            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }

        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayTotalAmount);

        // 因为 userId 是分片项.不能修改，所以在更新时设置成 null
        orders.setUserId(null);
        baseMapper.updateById(orders);

//        ordersMapper.insert(orders);

        // 3. 订单状态表
        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrderId(orderId);
        try {
            Thread.sleep(3 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        orderStatus.setOrderStatus(OrderStatus.OrderStatusEnum.WAIT_DELIVER.type);
        orderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(orderStatus);

        return OrderVO.builder()
                .orderId(orderId)
                .toBeRemovedList(toBeRemovedList)
                .build();
    }

    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {

    }

    /**
     * 从购物车获得商品
     *
     * @param list
     * @param specId
     * @return
     */
    private ShopCartBO getShopCartBOFromList(List<ShopCartBO> list, String specId) {
        for (ShopCartBO bo : list) {
            if (bo.getSpecId().equals(specId)) {
                return bo;
            }
        }
        return null;
    }
}

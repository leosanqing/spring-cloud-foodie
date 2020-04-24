package com.leosanqing.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: leosanqing
 * @Date: 2020/1/15 下午11:10
 * @Package: com.leosanqing.controller
 * @Description:
 */
@RestController
public class BaseController {

//    @Autowired
//    private RedisOperator redisOperator;

    public static final String SHOP_CART = "shopcart";
    public static final String REDIS_USER_TOKEN = "redis_user_token";


//    protected UsersVO convertUsersVO(Users users){
//        // 生成token，用于分布式会话
//        String uuid = UUID.randomUUID().toString().trim();
//        redisOperator.set(REDIS_USER_TOKEN+":"+users.getId(),uuid);
//
//
//        UsersVO usersVO = new UsersVO();
//        BeanUtils.copyProperties(users,usersVO);
//        usersVO.setUserUniqueToken(uuid);
//        return usersVO;
//    }

}

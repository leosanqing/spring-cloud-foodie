package com.leosanqing.user.service;


import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.bo.UserBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("foodie-user-service")
@RequestMapping("user-api")
public interface UserService {

    /**
     * 判断用户名是否存在
     */
    @GetMapping("user/exists")
    boolean queryUsernameIsExist(@RequestParam("username") String username);

    /**
     * 判断用户名是否存在
     */
    @PostMapping("user")
    Users createUser(@RequestBody UserBO userBO);

    /**
     * 检索用户名和密码是否匹配，用于登录
     */
    @GetMapping("verify")
    Users queryUserForLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password);
}

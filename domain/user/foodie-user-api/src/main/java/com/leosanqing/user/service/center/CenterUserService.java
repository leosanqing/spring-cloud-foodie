package com.leosanqing.user.service.center;


import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.bo.center.CenterUserBO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RequestMapping("center-user-api")
@FeignClient("foodie-user-service")
public interface CenterUserService {

    /**
     * 根据用户id查询用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("profile")
    Users queryUserInfo(@RequestParam("userId") String userId);

    /**
     * 修改用户信息
     *
     * @param userId
     * @param centerUserBO
     */
    @PutMapping("profile/{userId}")
    Users updateUserInfo(@PathVariable("userId") String userId, @RequestBody CenterUserBO centerUserBO);

    /**
     * 用户头像更新
     *
     * @param userId
     * @param faceUrl
     * @return
     */
    @PostMapping("updatePhoto")
    Users updateUserFace(@RequestParam("userId") String userId, @RequestParam("faceUrl") String faceUrl);
}

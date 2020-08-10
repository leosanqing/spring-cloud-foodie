package com.leosanqing.user.service.impl.center;


import com.leosanqing.user.mapper.UsersMapper;
import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.bo.center.CenterUserBO;
import com.leosanqing.user.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: leosanqing
 * @Date: 2019-12-15 20:05
 * @Package: com.leosanqing.service.center.impl
 * @Description: 用户中心服务实现类
 */
@RestController
public class CenterUserServiceImpl implements CenterUserService {

    @Resource
    private UsersMapper usersMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword(null);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users users = new Users();
        BeanUtils.copyProperties(centerUserBO, users);
        users.setId(userId);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);

        return queryUserInfo(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateUserFace(String userId, String faceUrl) {
        Users users = Users.builder()
                .id(userId)
                .face(faceUrl)
                .updatedTime(new Date())
                .build();
        usersMapper.updateByPrimaryKeySelective(users);

        return queryUserInfo(userId);

    }
}

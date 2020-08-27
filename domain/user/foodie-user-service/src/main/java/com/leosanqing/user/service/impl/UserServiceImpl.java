package com.leosanqing.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leosanqing.enums.Sex;
import com.leosanqing.user.mapper.UsersMapper;
import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.bo.UserBO;
import com.leosanqing.user.service.UserService;
import com.leosanqing.utils.DateUtil;
import com.leosanqing.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;

/**
 * @Author: leosanqing
 * @Date: 2019-12-06 00:16
 */
@RestController
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {

    private static final String FACE_PATH = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAllFXAAAclhVPdSg994" +
            ".png";

    @Autowired
    private Sid sid;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        return !lambdaQuery()
                .eq(Users::getUsername, username)
                .isEmptyOfEntity();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users createUser(UserBO userBO) {
        Users users = null;
        try {
            users = Users.builder()
                    .id(sid.nextShort())
                    .username(userBO.getUsername())
                    .password(MD5Utils.getMD5Str(userBO.getPassword()))
                    .face(FACE_PATH)
                    .birthday(DateUtil.stringToDate("1900-01-01"))
                    .nickname(userBO.getUsername())
                    .sex(Sex.SECRET.type)
                    .createdTime(new Date())
                    .updatedTime(new Date())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseMapper.insert(users);
        return users;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserForLogin(String username, String password) {
        return lambdaQuery()
                .eq(Users::getUsername, username)
                .eq(Users::getPassword, password)
                .one();
    }
}

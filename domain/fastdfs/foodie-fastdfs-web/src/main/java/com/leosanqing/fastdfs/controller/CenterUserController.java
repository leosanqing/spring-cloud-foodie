package com.leosanqing.fastdfs.controller;

import com.leosanqing.controller.BaseController;

import com.leosanqing.fastdfs.config.resource.FileResource;
import com.leosanqing.fastdfs.service.FdfsService;
import com.leosanqing.pojo.JSONResult;
import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.vo.UsersVO;
import com.leosanqing.user.service.center.CenterUserService;
import com.leosanqing.utils.CookieUtils;
import com.leosanqing.utils.JsonUtils;
import com.leosanqing.utils.RedisOperator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: leosanqing
 * @Date: 2019-12-15 20:35
 * @Package: com.leosanqing.controller.center
 * @Description: TODO
 */
@RestController
@RequestMapping("fdfs")
public class CenterUserController extends BaseController {

    @Autowired
    private FdfsService fdfsService;

    @Autowired
    private FileResource fileResource;

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private RedisOperator redisOperator;


    @PostMapping("uploadFace")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息", httpMethod = "POST")
    public JSONResult queryUserInfo(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response

    ) throws IOException {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户名id为空");
        }

        if (file == null) {
            return JSONResult.errorMsg("文件不能为空");
        }


        String path = "";
        String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename)) {
            final String[] split = StringUtils.split(filename, "\\.");
            final String suffix = split[split.length - 1];
            if (!"png".equalsIgnoreCase(suffix)
                    && !"jpg".equalsIgnoreCase(suffix)
                    && !"jpeg".equalsIgnoreCase(suffix)) {

                return JSONResult.errorMsg("图片格式不正确");
            }

            path = fdfsService.upload(file, suffix);
            System.out.println(path);
        }
        if (StringUtils.isBlank(path)) {
            return JSONResult.errorMsg("上传用户头像失败");
        } else {


            String finalUserServerUrl = fileResource.getHost() + path;

            Users users = centerUserService.updateUserFace(userId, finalUserServerUrl);


            // 后续增加令牌 整合进redis
            UsersVO usersVO = convertUsersVO(users);
            CookieUtils.setCookie(request, response, "user",
                    JsonUtils.objectToJson(usersVO), true);
        }


        return JSONResult.ok();

    }

    private UsersVO convertUsersVO(Users users) {
        // 生成token，用于分布式会话
        String uuid = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + users.getId(), uuid);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserUniqueToken(uuid);
        return usersVO;
    }
}

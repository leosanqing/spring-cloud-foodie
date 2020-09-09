package com.leosanqing.user.controller.center;

import com.leosanqing.constant.IResultCode;
import com.leosanqing.controller.BaseController;
import com.leosanqing.exception.BaseRuntimeException;
import com.leosanqing.user.pojo.Users;
import com.leosanqing.user.pojo.bo.center.CenterUserBO;
import com.leosanqing.user.pojo.vo.UsersVO;
import com.leosanqing.user.resource.FileUpload;
import com.leosanqing.user.service.center.CenterUserService;
import com.leosanqing.utils.CookieUtils;
import com.leosanqing.utils.DateUtil;
import com.leosanqing.utils.JsonUtils;
import com.leosanqing.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.leosanqing.constant.CommonResultCode.FILE_TYPE_NOT_SUPPORT;

/**
 * @Author: leosanqing
 * @Date: 2019-12-15 20:35
 * @Package: com.leosanqing.controller.center
 * @Description: 用户中心控制层
 */
@Api
@RestController
@RequestMapping("userInfo")
@Validated
public class CenterUserController extends BaseController {
//    public static final String USER_FACE_IMG_LOCATION =
//            File.separator + "Users" +
//                    File.separator + "zhuerchong" +
//                    File.separator + "Desktop" +
//                    File.separator + "code" +
//                    File.separator + "idea" +
//                    File.separator + "foodie-dev" +
//                    File.separator + "img";

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @PostMapping("update")
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息", httpMethod = "POST")
    public UsersVO updateUserInfo(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam @NotBlank(message = "userId 不能为空") String userId,
            @ApiParam(name = "centerUserBO", value = "用户中心bo")
            @RequestBody @Validated CenterUserBO centerUserBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Users users = centerUserService.updateUserInfo(userId, centerUserBO);

        // 后续增加令牌 整合进redis

        UsersVO usersVO = convertUsersVO(users);
//        setNullProperty(users);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);

        return usersVO;
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


    @PostMapping("uploadFace")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息", httpMethod = "POST")
    public void queryUserInfo(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @NotBlank(message = "userId 不能为空")
            @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true)
                    MultipartFile file,
            HttpServletRequest request,
            HttpServletResponse response

    ) {
        String userFaceImgPrefix = File.separator + userId;

        if (file == null) {
            throw new RuntimeException("文件不能为空");
        }


        final String filename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(filename)) {
            final String[] split = StringUtils.split(filename, "\\.");
            final String suffix = split[split.length - 1];
            if (!"png".equalsIgnoreCase(suffix)
                    && !"jpg".equalsIgnoreCase(suffix)
                    && !"jpeg".equalsIgnoreCase(suffix)) {

                throw new BaseRuntimeException(FILE_TYPE_NOT_SUPPORT);
            }

            String newFileName = "face-" + userId + "." + split[split.length - 1];

            // 文件最终保存的路径
//                String finalPath = USER_FACE_IMG_LOCATION + userFaceImgPrefix + File.pathSeparator + newFileName;
            String finalPath =
                    fileUpload.getUserFaceImgLocation() + userFaceImgPrefix + File.separator + newFileName;


            // 用于提供给web服务
            userFaceImgPrefix += ("/" + newFileName);
            final File outFile = new File(finalPath);
            File parent = outFile.getParentFile();
            if (parent != null) {
                // 创建文件夹
                parent.mkdirs();
            }

            try (BufferedInputStream bin = new BufferedInputStream(file.getInputStream());
                 BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(outFile))) {
                int b;
                while ((b = bin.read()) != -1) {
                    bout.write(b);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String finalUserServerUrl = fileUpload.getImgServerUrl() +
                userFaceImgPrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

        final Users users = centerUserService.updateUserFace(userId, finalUserServerUrl);


        // 后续增加令牌 整合进redis
        UsersVO usersVO = convertUsersVO(users);
//        setNullProperty(users);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);

    }

    /**
     * @param result
     * @return
     */
    private Map<String, String> getErrors(BindingResult result) {

        final HashMap<String, String> map = new HashMap<>();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String errorField = fieldError.getField();
            final String defaultMessage = fieldError.getDefaultMessage();
            map.put(errorField, defaultMessage);
        }


        return map;
    }


}

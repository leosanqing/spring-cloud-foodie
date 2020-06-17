package com.leosanqing.user.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: leosanqing
 * @Date: 2019-12-06 08:28
 */

@ApiModel(value = "用户对象的BO", description = "从客户端界面传输到后端的对象，封装到此Entity")
@Data
public class UserBO {
    @ApiModelProperty(value = "用户名", name = "username", example = "leosanqing", required = true)
    private String username;
    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    private String password;
    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = false)
    private String confirmPassword;
}

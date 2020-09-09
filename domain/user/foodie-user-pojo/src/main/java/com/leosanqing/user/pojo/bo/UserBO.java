package com.leosanqing.user.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @Author: leosanqing
 * @Date: 2019-12-06 08:28
 */

@ApiModel(value = "用户对象的BO", description = "从客户端界面传输到后端的对象，封装到此Entity")
@Data
public class UserBO {
    @ApiModelProperty(value = "用户名", name = "username", example = "leosanqing", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    @Length(min = 6, max = 18, message = "密码长度必须为6-18位")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "确认密码", name = "confirmPassword", example = "123456", required = false)
    @Length(min = 6, max = 18, message = "密码长度必须为6-18位")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}

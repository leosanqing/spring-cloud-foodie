package com.leosanqing.user.pojo.bo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @Author: leosanqing
 * @Date: 2019-12-14 09:03
 * @Package: com.leosanqing.pojo.bo
 * @Description: 地址对象
 */

@Data
public class AddressBO {

    private String addressId;

    private String userId;

    @NotBlank(message = "收货人不能为空")
    private String receiver;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$", message = "手机号不符合规范")
    @Length(max = 11, message = "手机号长度不能超过 11位")
    private String mobile;

    @NotBlank(message = "收货信息不能为空")
    private String province;

    @NotBlank(message = "收货信息不能为空")
    private String city;

    @NotBlank(message = "收货信息不能为空")
    private String district;

    @NotBlank(message = "收货信息不能为空")
    private String detail;
}

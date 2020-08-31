package com.leosanqing.auth.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: rtliu
 * @Date: 2020/8/13 下午5:07
 * @Package: PACKAGE_NAME
 * @Description: 账户
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements Serializable {

    private String userId;

    private String token;

    private String refreshToken;

}

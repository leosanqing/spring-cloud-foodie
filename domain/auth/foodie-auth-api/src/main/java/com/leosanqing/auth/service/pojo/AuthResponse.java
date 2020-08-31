package com.leosanqing.auth.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: rtliu
 * @Date: 2020/8/13 下午7:33
 * @Package: com.leosanqing.springcloud
 * @Description: 返回数据
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private Account account;

    private Long code;

}

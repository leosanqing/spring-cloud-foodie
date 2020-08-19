package com.leosanqing.auth.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: rtliu
 * @Date: 2020/8/13 下午5:11
 * @Package: com.leosanqing.springcloud
 * @Description: 返回状态码
 * @Version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class ResultCode {

    public static final Long SUCCESS = 1L;

    public static final Long INCORRECT_PWD = 1000L;

    public static final Long USER_NOT_FOUND = 1001L;

    public static final Long INVALID_TOKEN = 1002L;

}

package com.leosanqing.auth.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: rtliu
 * @Date: 2020/8/31 下午1:03
 * @Package: com.leosanqing.auth.service
 * @Description: 返回值枚举
 * @Version: 1.0
 */
@AllArgsConstructor
public enum AuthEnum {
    SUCCESS(1L),
    USER_NOT_FOUND(100L),
    INVALID_CREDENTIAL(100L),
    ;

    @Getter
    private Long code;
}

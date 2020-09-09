package com.leosanqing.constant;


public interface PatternConstants {
    String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    String MUST_NUMBER = "^[0-9]*$";
    String PHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
    String VERIFY_CODE = "^\\d{6}$";
    String PAY_PASSWORD = "^\\d{6}$";
    String SOCIAL_CREDIT_CODE = "[A-Z0-9]{18}$";
}

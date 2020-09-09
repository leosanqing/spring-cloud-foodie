package com.leosanqing.constant;

public enum CommonResultCode implements IResultCode {
    SYSTEM_LOGIC_ERROR(9001, "系统错误"),
    RPC_ERROR(9002, "远程服务调用失败"),
    AUTHENTICATION_ERROR(9003, "验证失败 请重新登录"),
    SESSION_INVALID(9004, "已在其他地方重新登录 请确认"),
    LOGIN_FAIL(9005, "请确认用户名存在或者密码是否正确"),
    FILE_TYPE_NOT_SUPPORT(9006, "文件类型不支持"),
    REDIS_LOCK_ERROR(9007, "竞争激烈，请稍后再试"),
    VERIFY_CODE_UNAVAILABLE(9007, "验证码校验失败 请确认"),
    FILE_OVERSIZE(9008, "上传文件超过支持文件大小"),
    FUNCTION_UNAVAILABLE(9009, "功能不可用"),

    VERIFY_SIGN_HEAD_MISS(9101, "sign api head param miss"),
    VERIFY_SIGN_PUBLIC_KEY_MISS(9102, "sign api public key miss"),
    VERIFY_SIGN_FAIL(9103, "sign api check error"),

    XSTORE_UPLOAD_FAIL(9201, "xstore upload error"),
    XSTORE_DOWNLOAD_FAIL(9202, "xstore download error"),

    CHAIN_CODE_LACK_CHAIN_CODE_IP(9301, "缺少chainCodeIp"),
    CHAIN_CODE_REQUEST_ERROR(9302, "上链网络访问请求失败"),
    CHAIN_CODE_SAVE_DEALING_RECORD_ERROR(9303, "交易记录上链失败"),
    CHAIN_CODE_SAVE_CHECK_SLIP_ERROR(9304, "结算记录上链失败"),
    CHAIN_CODE_SIGN_ERROR(9305, "上链签名失败"),
    CHAIN_CODE_QUERY_DEALING_RECORD_ERROR(9306, "查询链上交易记录失败"),
    CHAIN_CODE_QUERY_PERSONAL_WALLET_ERROR(9307, "查询链上个人钱包信息失败"),
    CHAIN_CODE_QUERY_BUSINESS_WALLET_ERROR(9308, "查询链上商家钱包信息失败"),
    ;

    private int code;
    private String desc;

    CommonResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMessage() {
        return this.desc;
    }
}

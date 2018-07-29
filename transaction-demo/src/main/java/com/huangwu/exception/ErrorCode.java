package com.huangwu.exception;

/**
 * @Package: com.huangwu.common
 * @Author: huangwu
 * @Date: 2018/5/19 15:08
 * @Description:
 * @LastModify:
 */
public enum ErrorCode {
    SUCCESS("200", "操作成功"),
    SERVER_ERROR("999", "系统错误"),

    // etcd相关操作异常
    ETCD_KEY_NOT_EXIST("100001", "当前key不存在"),
    ERCD_ADDRESS_ERROR("100002", "etcd地址不正确，正确格式为ip:port"),
    ERCD_ADDRESS_NOT_EXIST("100003", "etcd地址不存在"),
    ETCD_PATH_EXIST("100004", "当前path已存在"),
    ETCD_PARAMETER_ERROR("100005", "参数校验错误"),
    ETCD_CONFIG_FILE_EMPTY("100006", "配置文件内容不能为空"),


    // 用户相关异常
    USER_NAME_EXIST("200001", "该用户名已经被使用"),
    USER_PHONE_EXIST("200002", "该手机号码已经被注册"),
    USER_ACCOUNT_ERROR("200003", "用户名或手机号错误"),
    USER_PASSWORD_ERROR("200004", "用户密码错误"),
    SESSION_ERROR("200005", "Session不存在或者已经失效"),

    // 邮件相关
    MAIL_SEND_ERROR("300001", "邮件发送错误"),
    INVALID_MAIL_ADDRESS("300002", "邮件发送失败，请检查邮件地址是否正确"),
    EMAIL_ALREADY_REGISTERED("300003", "该邮箱已经被注册"),
    EMAIL_VERIFY_CODE_ERROR("300004", "邮箱注册码错误"),

    //秒杀相关
    SECKILL_PARAM_ERROR("400001", "参数解析错误"),
    ACCESS_LIMIT_REACHED("400002", "访问太频繁"),

    //其他异常
    ROUTE_UNDEFINED("500001", "未定义的路由规则"),
    NO_UNIQUE_BEAN_DEFINITION("500002","未定义的bean对象"),
    ;

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

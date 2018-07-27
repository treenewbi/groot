package com.huangwu.common;

/**
 * restfull请求结果体
 *
 * @Package: com.huangwu.common
 * @Author: huangwu
 * @Date: 2018/5/19 13:13
 * @Description:
 * @LastModify:
 */
public class Result<T> {

    private String code;
    private String message;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private Result() {}

    public static <T> Result<T> succees(T data) {
        Result result = new Result(ErrorCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return new Result<>(errorCode);
    }

    public static <T> Result<T> error(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public Result(ErrorCode errorCode) {
        if (errorCode != null) {
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
        }
    }
}

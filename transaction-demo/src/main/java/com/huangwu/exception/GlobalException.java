package com.huangwu.exception;

/**
 * @Package: com.huangwu.exception
 * @Author: huangwu
 * @Date: 2018/5/24 11:54
 * @Description:
 * @LastModify:
 */
public class GlobalException extends Exception {

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private String message;

    public GlobalException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.huangwu.domain.vo;

import javax.validation.constraints.NotNull;

/**
 * @Package: com.huangwu.domain.vo
 * @Author: huangwu
 * @Date: 2018/5/27 19:30
 * @Description:
 * @LastModify:
 */
public class UserVo {

    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String verifyCode;
    private String email;
    private Integer age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

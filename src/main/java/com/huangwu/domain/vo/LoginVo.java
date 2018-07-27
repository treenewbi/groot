package com.huangwu.domain.vo;

import com.huangwu.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Package: com.huangwu.domain.vo
 * @Author: huangwu
 * @Date: 2018/5/19 19:01
 * @Description:
 * @LastModify:
 */
public class LoginVo {

    /**
     * 允许用户名或者手机号进行登录
     */
    @NotNull
    @Length(max = 11)
    private String account;

    @NotNull
    @Length(min = 8)
    private String password;

    public LoginVo(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public LoginVo() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

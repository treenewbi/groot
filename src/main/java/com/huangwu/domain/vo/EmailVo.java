package com.huangwu.domain.vo;

import javax.validation.constraints.NotNull;

/**
 * @Package: com.huangwu.domain.vo
 * @Author: huangwu
 * @Date: 2018/5/31 11:23
 * @Description:
 * @LastModify:
 */
public class EmailVo {
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

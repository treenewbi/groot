package com.huangwu.domain.vo;

import javax.validation.constraints.NotNull;

/**
 * 秒杀用户Vo
 *
 * @Package: com.huangwu.domain.vo
 * @Author: huangwu
 * @Date: 2018/6/2 21:08
 * @Description:
 * @LastModify:
 */
public class SeckillUserVo {


    private Long userId;
    private String userName;
    @NotNull
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    @Override
    public String toString() {
        return "SeckillUserVo{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

package com.huangwu.domain;

import java.util.Date;

/**
 * 用户实体
 *
 * @Package: com.huangwu.domain
 * @Author: huangwu
 * @Date: 2018/5/27 11:09
 * @Description:
 * @LastModify:
 */
public class GrootUser {
    /**
     * id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户手机
     */
    private String userPhone;
    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 上次登录时间
     */
    private Date lastLoginTime;
    /**
     * 登录次数
     */
    private Long loginTimes;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 是否删除
     */
    private int isDeleted;

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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Long loginTimes) {
        this.loginTimes = loginTimes;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "GrootUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", loginTimes=" + loginTimes +
                ", roleId=" + roleId +
                ", isDeleted=" + isDeleted +
                '}';
    }
}

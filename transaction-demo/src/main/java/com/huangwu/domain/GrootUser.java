package com.huangwu.domain;

import lombok.Data;

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
@Data
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

}

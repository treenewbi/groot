package com.huangwu.service;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/16 12:50
 * @Description:
 * @LastModify:
 */
public interface IUserDeleteOperation extends OperationRoute {
    Integer deleteUserById(long userId) throws Exception;
}

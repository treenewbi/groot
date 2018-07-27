package com.huangwu.service;

import com.huangwu.domain.GrootUser;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/16 12:49
 * @Description:
 * @LastModify:
 */
public interface IUserUpdateOperation extends OperationRoute {

    Integer updateUser(GrootUser user) throws Exception;

    Integer updatePhoneById(long userId, String phone) throws Exception;

    Integer updateRoleIdById(long userId, long roleId) throws Exception;
}

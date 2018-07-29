package com.huangwu.service;

import com.huangwu.domain.GrootUser;

import java.util.Date;
import java.util.List;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/16 11:54
 * @Description:
 * @LastModify:
 */
public interface IUserOperationService {

    GrootUser queryUserById(long userId) throws Exception;

    List<GrootUser> queryUsersByRoleId(long roleId) throws Exception;

    List<GrootUser> queryUsersByLoginTime(Date begin, Date end) throws Exception;

    Integer updateUser(GrootUser user) throws Exception;

    Integer updatePhoneById(long userId, String phone) throws Exception;

    Integer updateRoleIdById(long userId, long roleId) throws Exception;

    Integer addUser(GrootUser user) throws Exception;

    Integer deleteUserById(long userId) throws Exception;
}

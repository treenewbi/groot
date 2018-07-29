package com.huangwu.service;

import com.huangwu.domain.GrootUser;

import java.util.Date;
import java.util.List;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/16 12:49
 * @Description:
 * @LastModify:
 */
public interface IUserQueryOperation extends OperationRoute {

    GrootUser queryUserById(long userId) throws Exception;

    List<GrootUser> queryUsersByRoleId(long roleId) throws Exception;

    List<GrootUser> queryUsersByLoginTime(Date begin, Date end) throws Exception;
}

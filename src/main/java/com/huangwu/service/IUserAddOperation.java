package com.huangwu.service;

import com.huangwu.domain.GrootUser;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/16 12:51
 * @Description:
 * @LastModify:
 */
public interface IUserAddOperation extends OperationRoute {

    Integer addUser(GrootUser user) throws Exception;
}

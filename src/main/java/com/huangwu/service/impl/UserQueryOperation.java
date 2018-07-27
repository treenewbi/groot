package com.huangwu.service.impl;

import com.huangwu.common.Request;
import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.mapper.UserMapper;
import com.huangwu.service.IUserQueryOperation;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.Request;
import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.exception.GlobalException;
import com.huangwu.mapper.UserMapper;
import com.huangwu.service.IUserQueryOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/7/17 20:36
 * @Description:
 * @LastModify:
 */
@Service
public class UserQueryOperation implements IUserQueryOperation {
    private final String QUERY_USER_BY_ID = "queryUserById";
    private final String QUERY_USERS_BY_ROLEID = "queryUsersByRoleId";
    private final String QUERY_USERS_BY_LOGIN_TIME = "queryUsersByLoginTime";

    @Resource
    private UserMapper userMapper;

    @Override
    public GrootUser queryUserById(long userId) throws Exception {
        return userMapper.queryUserById(userId);
    }

    @Override
    public List<GrootUser> queryUsersByRoleId(long roleId) throws Exception {
        return userMapper.queryUsersByRoleId(roleId);
    }

    @Override
    public List<GrootUser> queryUsersByLoginTime(Date begin, Date end) throws Exception {
        return userMapper.queryUsersByLoginTime(begin, end);
    }

    @Override
    public Result route(Request request) throws Exception {
        if (request.getOperationMethod().equals(QUERY_USER_BY_ID)) {
            GrootUser user = queryUserById((Long) request.getParamMap().get("userId"));
            return Result.succees(user);
        } else if (request.getOperationMethod().equals(QUERY_USERS_BY_ROLEID)) {
            List<GrootUser> users = userMapper.queryUsersByRoleId((Long) request.getParamMap().get("roleId"));
            return Result.succees(users);
        } else if (request.getOperationMethod().equals(QUERY_USERS_BY_LOGIN_TIME)) {
            List<GrootUser> users = userMapper.queryUsersByLoginTime((Date) request.getParamMap().get("begin"), (Date) request.getParamMap().get("end"));
            return Result.succees(users);
        } else {
            throw new GlobalException(ErrorCode.ROUTE_UNDEFINED);
        }
    }
}

package com.huangwu.service.impl;

import com.huangwu.service.IUserAddOperation;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.Request;
import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.exception.GlobalException;
import com.huangwu.mapper.UserMapper;
import com.huangwu.service.IUserAddOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/7/17 20:53
 * @Description:
 * @LastModify:
 */
@Service
public class UserAddOperation implements IUserAddOperation {
    private final String ADD_USER = "addUser";

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer addUser(GrootUser user) throws Exception {
        return userMapper.addUser(user);
    }

    @Override
    public Result route(Request request) throws Exception {
        if (request.getOperationMethod().equals(ADD_USER)) {
            Integer result = addUser((GrootUser) request.getParamMap().get("user"));
            return Result.succees(request);
        } else {
            throw new GlobalException(ErrorCode.ROUTE_UNDEFINED);
        }
    }
}

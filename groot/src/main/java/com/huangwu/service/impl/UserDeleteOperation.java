package com.huangwu.service.impl;

import com.huangwu.service.IUserDeleteOperation;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.Request;
import com.huangwu.common.Result;
import com.huangwu.exception.GlobalException;
import com.huangwu.mapper.UserMapper;
import com.huangwu.service.IUserDeleteOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/7/17 21:04
 * @Description:
 * @LastModify:
 */
@Service
public class UserDeleteOperation implements IUserDeleteOperation {

    private final String DELETE_USER_BY_ID = "deleteUserById";

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer deleteUserById(long userId) throws Exception {
        return userMapper.deleteUserById(userId);
    }

    @Override
    public Result route(Request request) throws Exception {
        if (request.getOperationMethod().equals(DELETE_USER_BY_ID)) {
            int succeessNum = deleteUserById((Long) request.getParamMap().get("userId"));
            return Result.succees(succeessNum);
        } else {
            throw new GlobalException(ErrorCode.ROUTE_UNDEFINED);
        }
    }
}

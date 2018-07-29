package com.huangwu.service.impl;

import com.huangwu.common.ErrorCode;
import com.huangwu.common.Request;
import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.exception.GlobalException;
import com.huangwu.mapper.UserMapper;
import com.huangwu.service.IUserUpdateOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/7/17 20:56
 * @Description:
 * @LastModify:
 */
@Service
public class UserUpdateOperation implements IUserUpdateOperation {

    private final String UPDATE_USER = "updateUser";
    private final String UPDATE_PHONE_BY_ID = "updatePhoneById";
    private final String UPDATE_ROLE_ID_BY_ID = "updateRoleIdById";
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer updateUser(GrootUser user) throws Exception {
        return userMapper.updateUser(user);
    }

    @Override
    public Integer updatePhoneById(long userId, String phone) throws Exception {
        return userMapper.updatePhoneById(userId, phone);
    }

    @Override
    public Integer updateRoleIdById(long userId, long roleId) throws Exception {
        return userMapper.updateRoleIdById(userId, roleId);
    }

    @Override
    public Result route(Request request) throws Exception {
        if (request.getOperationMethod().equals(UPDATE_USER)) {
            int succeessNum = updateUser((GrootUser) request.getParamMap().get("user"));
            return Result.succees(succeessNum);
        } else if (request.getOperationMethod().equals(UPDATE_PHONE_BY_ID)) {
            int succeessNum = updatePhoneById((Long) request.getParamMap().get("userId"), (String) request.getParamMap().get("phone"));
            return Result.succees(succeessNum);
        } else if (request.getOperationMethod().equals(UPDATE_ROLE_ID_BY_ID)) {
            int scceessNum = updateRoleIdById((Long) request.getParamMap().get("userId"), (Long) request.getParamMap().get("roleId"));
            return Result.succees(scceessNum);
        } else {
            throw new GlobalException(ErrorCode.ROUTE_UNDEFINED);
        }
    }
}

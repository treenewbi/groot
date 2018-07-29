package com.huangwu.service.impl;

import com.huangwu.service.IUserOperationService;
import com.huangwu.domain.GrootUser;
import com.huangwu.mapper.UserMapper;
import com.huangwu.service.IUserOperationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/7/16 11:56
 * @Description:
 * @LastModify:
 */
@Service
public class UserOperationService implements IUserOperationService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer addUser(GrootUser user) throws Exception {
        return userMapper.addUser(user);
    }

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
    public Integer deleteUserById(long userId) throws Exception {
        return userMapper.deleteUserById(userId);
    }
}

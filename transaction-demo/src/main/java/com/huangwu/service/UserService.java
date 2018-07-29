package com.huangwu.service;

import com.huangwu.domain.GrootUser;
import com.huangwu.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/7/29 15:50
 * @Description:
 * @LastModify:
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional
    public Integer addUser(GrootUser user) throws Exception {
        return userMapper.addUser(user);
    }

    @Transactional
    public boolean batchAddUser(List<GrootUser> users) {
        for (GrootUser user : users) {
            userMapper.addUser(user);
        }
        return true;
    }
}

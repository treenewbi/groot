package com.huangwu.service;

import com.huangwu.domain.EtcdModify;
import com.huangwu.domain.GrootUser;
import com.huangwu.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    @Resource
    private EtcdModifyService etcdModifyService;

    @Transactional(rollbackFor = Exception.class)
    public Integer addUser(GrootUser user) {
        return userMapper.addUser(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean batchAddUser(List<GrootUser> users) {
        for (GrootUser user : users) {
            userMapper.addUser(user);
        }
        return true;
    }

    /**
     * 测试嵌套事物
     *
     * @param user
     * @param modify
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void addUserAndEtcdModify(GrootUser user, EtcdModify modify) {
        addUser(user);
        etcdModifyService.addEtcdModify(modify);
    }
}

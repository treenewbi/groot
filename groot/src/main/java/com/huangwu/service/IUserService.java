package com.huangwu.service;

import com.huangwu.domain.GrootUser;
import com.huangwu.domain.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/5/27 11:27
 * @Description:
 * @LastModify:
 */
public interface IUserService {
    /**
     * 注册用户
     *
     * @param user
     * @return
     * @throws Exception
     */
    boolean register(GrootUser user) throws Exception;

    GrootUser login(HttpServletResponse response, LoginVo loginVo) throws Exception;

    /**
     * 根据token查询用户，并延迟cookie
     *
     * @param response
     * @param token
     * @return
     * @throws Exception
     */
    GrootUser getByToken(HttpServletResponse response, String token) throws Exception;

    GrootUser queryUser(String username) throws Exception;

    void addUser(GrootUser user) throws Exception;
}

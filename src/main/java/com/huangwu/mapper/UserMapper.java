package com.huangwu.mapper;

import com.huangwu.domain.GrootUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Package: com.huangwu.mapper
 * @Author: huangwu
 * @Date: 2018/5/27 11:31
 * @Description:
 * @LastModify:
 */
@Mapper
public interface UserMapper {
    GrootUser findUserByName(String username) throws Exception;

    GrootUser findUserByPhone(String phpne) throws Exception;

    @Insert("INSERT INTO groot.t_groot_user (user_name, user_phone, user_password,email, user_salt, last_login_time, login_times, role_id, is_deleted) " +
            "VALUES (#{userName}, #{userPhone}, #{password}, #{email}, #{salt}, #{lastLoginTime}, #{loginTimes}, #{roleId}, #{isDeleted})")
    Integer insertUser(GrootUser user) throws Exception;

    GrootUser checkUserByNameOrPhone(@Param("account") String account, @Param("password") String password) throws Exception;

    GrootUser getUserByNameOrPhone(String account) throws Exception;

    GrootUser queryUserByEmail(String email) throws Exception;

    GrootUser queryUser(String username) throws Exception;

    Integer addUser(GrootUser user) throws Exception;

    GrootUser queryUserById(long userId) throws Exception;

    List<GrootUser> queryUsersByRoleId(long roleId) throws Exception;

    List<GrootUser> queryUsersByLoginTime(@Param("begin") Date begin, @Param("end") Date end) throws Exception;

    Integer updateUser(GrootUser user) throws Exception;

    Integer updatePhoneById(@Param("userId") long userId, @Param("phone") String phone) throws Exception;

    Integer updateRoleIdById(@Param("userId") long userId, @Param("roleId") long roleId) throws Exception;

    Integer deleteUserById(long userId) throws Exception;
}

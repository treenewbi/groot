package com.huangwu.api;

import com.huangwu.common.Request;
import com.huangwu.common.Request;
import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;

import java.util.List;

/**
 * 提供grootUser表的相关增删改查操作接口
 *
 * @Package: com.huangwu.api
 * @Author: huangwu
 * @Date: 2018/7/16 11:02
 * @Description:
 * @LastModify:
 */
public interface UserOperationApi {

    Result<List<GrootUser>> queryUser(Request request) throws Exception;

    Result<Integer> updateUser(Request request) throws Exception;

    Result<Integer> addUser(Request request) throws Exception;

    Result<Integer> deleteUserById(Request request) throws Exception;
}

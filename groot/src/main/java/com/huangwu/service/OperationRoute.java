package com.huangwu.service;

import com.huangwu.common.Request;
import com.huangwu.common.Result;

/**
 * 路由基类接口
 *
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/16 12:41
 * @Description:
 * @LastModify:
 */
public interface OperationRoute<T extends Result> {

    T route(Request request) throws Exception;

}

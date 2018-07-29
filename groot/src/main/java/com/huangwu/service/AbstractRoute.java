package com.huangwu.service;

import com.huangwu.OperationEnum;
import com.huangwu.common.OperationFactory;
import com.huangwu.common.Request;
import com.huangwu.service.impl.UserAddOperation;
import com.huangwu.service.impl.UserDeleteOperation;
import com.huangwu.service.impl.UserUpdateOperation;
import com.huangwu.OperationEnum;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.OperationFactory;
import com.huangwu.common.Request;
import com.huangwu.common.Result;
import com.huangwu.exception.GlobalException;
import com.huangwu.service.impl.UserAddOperation;
import com.huangwu.service.impl.UserDeleteOperation;
import com.huangwu.service.impl.UserQueryOperation;
import com.huangwu.service.impl.UserUpdateOperation;

import javax.annotation.Resource;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/16 12:47
 * @Description:
 * @LastModify:
 */
public abstract class AbstractRoute {

    @Resource
    private OperationFactory operationFactory;

    public final Result process(Request request) throws Exception {
        request = checkBeforeProcess(request);
        if (request.getOperationType().equals(OperationEnum.AddOperation.getType())) {
            return operationFactory.getOperation(UserAddOperation.class).route(request);
        } else if (request.getOperationType().equals(OperationEnum.DeleteOperation.getType())) {
            return operationFactory.getOperation(UserDeleteOperation.class).route(request);
        } else if (request.getOperationType().equals(OperationEnum.UpdateOperation.getType())) {
            return operationFactory.getOperation(UserUpdateOperation.class).route(request);
        } else if (request.getOperationType().equals(OperationEnum.QueryOperation.getType())) {
            return operationFactory.getOperation(UserQueryOperation.class).route(request);
        }
        throw new GlobalException(ErrorCode.ROUTE_UNDEFINED);
    }

    protected abstract Request checkBeforeProcess(Request request) throws Exception;
}

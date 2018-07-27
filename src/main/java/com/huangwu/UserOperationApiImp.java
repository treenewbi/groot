package com.huangwu;

import com.huangwu.api.UserOperationApi;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.Request;
import com.huangwu.common.Result;
import com.huangwu.domain.GrootUser;
import com.huangwu.exception.GlobalException;
import com.huangwu.service.AbstractRoute;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Package: com.huangwu
 * @Author: huangwu
 * @Date: 2018/7/17 21:13
 * @Description:
 * @LastModify:
 */
@Component
public class UserOperationApiImp extends AbstractRoute implements UserOperationApi {
    @Override
    public Result<List<GrootUser>> queryUser(Request request) throws Exception {
        return process(request);
    }

    @Override
    public Result<Integer> updateUser(Request request) throws Exception {
        return process(request);
    }

    @Override
    public Result<Integer> addUser(Request request) throws Exception {
        return process(request);
    }

    @Override
    public Result<Integer> deleteUserById(Request request) throws Exception {
        return process(request);
    }

    @Override
    protected Request checkBeforeProcess(Request request) throws Exception {
        if (request == null || StringUtils.isBlank(request.getSessionId())) {
            throw new GlobalException(ErrorCode.SESSION_ERROR);
        }
        return request;
    }


}

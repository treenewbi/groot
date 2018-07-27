package com.huangwu.etcd.service;

import com.huangwu.MainApplication;
import com.huangwu.api.UserOperationApi;
import com.huangwu.common.Request;
import com.huangwu.common.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * @Package: com.huangwu.etcd.service
 * @Author: huangwu
 * @Date: 2018/7/17 21:17
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class UserOperationApiTest {

    @Autowired
    private UserOperationApi userOperationApi;

    @Test
    public void queryUserTest() throws Exception {
        Request request = new Request();
        request.setOperationType("query");
        request.setOperationMethod("queryUserById");
        HashMap paramMap = new HashMap();
        paramMap.put("userId", 1L);
        request.setParamMap(paramMap);
        request.setSessionId("11");
        Result result = userOperationApi.queryUser(request);
        System.out.println(result.getData());
    }
}

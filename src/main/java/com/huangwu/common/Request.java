package com.huangwu.common;

import java.util.HashMap;

/**
 * @Package: com.huangwu.common
 * @Author: huangwu
 * @Date: 2018/7/16 12:45
 * @Description:
 * @LastModify:
 */
public class Request {
    private String operationType;
    private String operationMethod;
    private String sessionId;
    private HashMap<String, Object> paramMap = new HashMap<>();

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationMethod() {
        return operationMethod;
    }

    public void setOperationMethod(String operationMethod) {
        this.operationMethod = operationMethod;
    }

    public HashMap<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(HashMap<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

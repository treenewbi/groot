package com.huangwu.etcd;

import com.huangwu.common.json.JSONObject;
import com.huangwu.exception.JSONException;
import com.huangwu.common.json.JSONObject;
import com.huangwu.exception.JSONException;

public class EtcdErrorResponse implements EtcdResponse {
    int errorCode;
    String message;
    String cause;
    int index;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public EtcdErrorResponse() {
    }

    public EtcdErrorResponse(JSONObject json) throws JSONException {
        errorCode = json.getInt("errorCode");
        message = json.getString("message");
        if (json.has("cause")) {
            cause = json.getString("cause");
        }
        if (json.has("index")) {
            index = json.getInt("index");
        }
    }

    @Override
    public String toString() {
        return "EtcdErrorResponse{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", cause='" + cause + '\'' +
                ", index=" + index +
                '}';
    }
}

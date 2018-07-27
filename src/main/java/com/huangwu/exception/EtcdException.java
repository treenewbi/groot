package com.huangwu.exception;

import com.huangwu.etcd.EtcdErrorResponse;

public class EtcdException extends Exception {
    EtcdErrorResponse errorResponse;

    public EtcdException() {
    }

    public EtcdException(String message) {
        super(message);
    }

    public EtcdException(String message, Throwable cause) {
        super(message, cause);
    }

    public EtcdException(Throwable cause) {
        super(cause);
    }

    public EtcdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EtcdException(EtcdErrorResponse etcdErrorResponse) {
        this.errorResponse = etcdErrorResponse;
    }

    public EtcdErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(EtcdErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    @Override
    public String toString() {
        return "EtcdException{" +
                "errorResponse=" + errorResponse +
                '}';
    }
}

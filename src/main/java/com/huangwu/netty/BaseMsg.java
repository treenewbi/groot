package com.huangwu.netty;

import java.io.Serializable;

/**
 * @Package: com.huangwu.netty
 * @Author: huangwu
 * @Date: 2018/6/15 15:45
 * @Description:
 * @LastModify:
 */
public class BaseMsg implements Serializable {

    private static final long serialVersionUID = 1L;
    private MsgType type;
    //必须唯一，否者会出现channel调用混乱
    private String clientId;

    //初始化客户端id
    public BaseMsg() {
        this.clientId = Constants.getClientId();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }
}

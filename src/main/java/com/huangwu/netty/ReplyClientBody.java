package com.huangwu.netty;

/**
 * @Package: com.huangwu.netty
 * @Author: huangwu
 * @Date: 2018/6/15 15:52
 * @Description:
 * @LastModify:
 */
public class ReplyClientBody extends ReplyBody {
    private String clientInfo;

    public ReplyClientBody(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}

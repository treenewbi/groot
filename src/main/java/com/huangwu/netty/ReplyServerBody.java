package com.huangwu.netty;

/**
 * @Package: com.huangwu.netty
 * @Author: huangwu
 * @Date: 2018/6/15 15:53
 * @Description:
 * @LastModify:
 */
public class ReplyServerBody extends ReplyBody {
    private String serverInfo;

    public ReplyServerBody(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }
}

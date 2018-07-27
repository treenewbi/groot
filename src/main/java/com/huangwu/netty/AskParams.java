package com.huangwu.netty;

import java.io.Serializable;

/**
 * @Package: com.huangwu.netty
 * @Author: huangwu
 * @Date: 2018/6/15 15:46
 * @Description:
 * @LastModify:
 */
public class AskParams implements Serializable {

    private static final long serialVersionUID = 1L;
    private String auth;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}

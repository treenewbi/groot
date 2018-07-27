package com.huangwu.netty;

/**
 * @Package: com.huangwu.netty
 * @Author: huangwu
 * @Date: 2018/6/15 15:45
 * @Description:
 * @LastModify:
 */
public class AskMsg extends BaseMsg {
    public AskMsg() {
        super();
        setType(MsgType.ASK);

    }

    private AskParams params;

    public AskParams getParams() {
        return params;
    }

    public void setParams(AskParams params) {
        this.params = params;
    }
}

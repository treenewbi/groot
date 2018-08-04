package com.huangwu.etcd;


import com.huangwu.common.json.JSONObject;
import com.huangwu.exception.JSONException;
import com.huangwu.exception.JSONException;
import com.huangwu.common.json.JSONObject;

public class EtcdActionResponse implements EtcdResponse {
    String action;
    EtcdNode node;
    EtcdNode prevNode;
    // 是否注册成功
    boolean isRegister;

    public EtcdActionResponse() {

    }

    public EtcdActionResponse(JSONObject json) throws JSONException {
        if (json.has("leaderInfo")) {
            isRegister = true;
            return;
        }
        action = json.getString("action");
        node = new EtcdNode(json.getJSONObject("node"));
        if (json.has("prevNode")) {
            prevNode = new EtcdNode(json.getJSONObject("prevNode"));
        }
    }

    @Override
    public String toString() {
        return String.format("%s node:%s prevNode:%s", action, node, prevNode);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public EtcdNode getNode() {
        return node;
    }

    public void setNode(EtcdNode node) {
        this.node = node;
    }

    public EtcdNode getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(EtcdNode prevNode) {
        this.prevNode = prevNode;
    }

    public boolean isRegister() {
        return isRegister;
    }
}

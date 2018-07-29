package com.huangwu.etcd;

import com.huangwu.common.json.JSONArray;
import com.huangwu.common.json.JSONObject;
import com.huangwu.exception.JSONException;
import com.huangwu.util.PropertyUtil;
import com.huangwu.exception.JSONException;
import com.huangwu.common.json.JSONArray;
import com.huangwu.common.json.JSONObject;
import com.huangwu.util.PropertyUtil;

import javax.xml.bind.DatatypeConverter;
import java.util.*;

public class EtcdNode implements Comparable<EtcdNode> {
    private String key;
    private String value;
    private long modifiedIndex;
    private long createdIndex;
    private long ttl;
    private Date expiration;
    private List<EtcdNode> nodes;
    private boolean isDir;
    /**
     * 所有子节点的数量
     */
    private Integer nodesSize;


    public EtcdNode() {

    }

    public EtcdNode(JSONObject node) throws JSONException {
        try {
            key = node.getString("key");
        } catch (JSONException e) {
            key = node.optString("key", "/");
        }
        if (node.has("value"))
            value = node.getString("value");
        if (node.has("modifiedIndex"))
            modifiedIndex = node.getLong("modifiedIndex");
        if (node.has("dir"))
            isDir = node.getBoolean("dir");
        if (node.has("createdIndex"))
            createdIndex = node.getLong("createdIndex");
        if (node.has("expiration"))
            expiration = DatatypeConverter.parseDateTime(node.getString("expiration")).getTime();
        if (node.has("ttl"))
            ttl = node.getLong("ttl");
        if (node.has("nodes")) {
            JSONArray array = node.getJSONArray("nodes");
            this.nodesSize = array.length();
            nodes = new ArrayList<EtcdNode>();
            // etcd最大展示子节点数量
            int maxNodesSize = Integer.valueOf(PropertyUtil.getProperty("etcd.maxNodesSize", "200"));
            for (int i = 0; i < array.length(); i++) {
                if (i >= maxNodesSize)
                    break;
                JSONObject j = array.getJSONObject(i);
                EtcdNode n = new EtcdNode(j);
                nodes.add(n);
            }
            Collections.sort(nodes);
        } else {
            this.nodesSize = 0;
        }

    }

    @Override
    public String toString() {
        return String.format("{key:%s value:%s modifiedIndex:%d createIndex:%d ttl:%d expiration:%s}", key, value, modifiedIndex, createdIndex, ttl, expiration);
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        int index = key.lastIndexOf("/");
        if (index >= 0)
            return key.substring(index + 1);
        else
            return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getModifiedIndex() {
        return modifiedIndex;
    }

    public void setModifiedIndex(long modifiedIndex) {
        this.modifiedIndex = modifiedIndex;
    }

    public long getCreatedIndex() {
        return createdIndex;
    }

    public void setCreatedIndex(long createdIndex) {
        this.createdIndex = createdIndex;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public List<EtcdNode> getNodes() {
        return nodes;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Integer getNodesSize() {
        return nodesSize;
    }

    public void setNodesSize(Integer nodesSize) {
        this.nodesSize = nodesSize;
    }

    /**
     * 默认按key值排序
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(EtcdNode o) {
        return key.compareTo(o.key);
    }
}

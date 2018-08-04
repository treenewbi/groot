package com.huangwu.domain;

import java.util.Date;

/**
 * etcd修改实体
 *
 * @Package: com.huangwu.domain
 * @Author: huangwu
 * @Date: 2018/5/19 21:10
 * @Description:
 * @LastModify:
 */
public class EtcdModify {
    /**
     * id
     */
    private Long etcdModifyId;
    /**
     * etcd地址
     */
    private String etcdModifyAddr;
    /**
     * 修改后key
     */
    private String etcdModifyKey;
    /**
     * 修改后value
     */
    private String etcdModifyValue;
    /**
     * 修改后超时时间
     */
    private Long etcdModifyTtl;
    /**
     * 是否目录结构1：是；0：不是
     */
    private Integer isDir;
    /**
     * 修改用户id
     */
    private Long modifyUserid;
    /**
     * 修改时间
     */
    private Date modifiedTime;
    /**
     * 修改类型，1：insert，2：delete，3：update
     */
    private Integer modifyType;

    public EtcdModify(String etcdModifyAddr, String etcdModifyKey, String etcdModifyValue, Long etcdModifyTtl, Boolean isDir, Long modifyUserid, Integer modifyType) {
        this.etcdModifyAddr = etcdModifyAddr;
        this.etcdModifyKey = etcdModifyKey;
        this.etcdModifyValue = etcdModifyValue;
        if (etcdModifyTtl == null) {
            this.etcdModifyTtl = 0L;
        } else {
            this.etcdModifyTtl = etcdModifyTtl;
        }
        this.etcdModifyTtl = etcdModifyTtl;
        if (isDir) {
            this.isDir = 1;
        } else {
            this.isDir = 0;
        }
        this.modifyUserid = modifyUserid;
        this.modifyType = modifyType;
    }

    public Long getEtcdModifyId() {
        return etcdModifyId;
    }

    public void setEtcdModifyId(Long etcdModifyId) {
        this.etcdModifyId = etcdModifyId;
    }

    public String getEtcdModifyKey() {
        return etcdModifyKey;
    }

    public void setEtcdModifyKey(String etcdModifyKey) {
        this.etcdModifyKey = etcdModifyKey;
    }

    public String getEtcdModifyValue() {
        return etcdModifyValue;
    }

    public void setEtcdModifyValue(String etcdModifyValue) {
        this.etcdModifyValue = etcdModifyValue;
    }

    public Long getModifyUserid() {
        return modifyUserid;
    }

    public void setModifyUserid(Long modifyUserid) {
        this.modifyUserid = modifyUserid;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getModifyType() {
        return modifyType;
    }

    public void setModifyType(Integer modifyType) {
        this.modifyType = modifyType;
    }

    public Long getEtcdModifyTtl() {
        return etcdModifyTtl;
    }

    public void setEtcdModifyTtl(Long etcdModifyTtl) {
        this.etcdModifyTtl = etcdModifyTtl;
    }

    public Integer getIsDir() {
        return isDir;
    }

    public void setIsDir(Integer isDir) {
        this.isDir = isDir;
    }

    public String getEtcdModifyAddr() {
        return etcdModifyAddr;
    }

    public void setEtcdModifyAddr(String etcdModifyAddr) {
        this.etcdModifyAddr = etcdModifyAddr;
    }
}

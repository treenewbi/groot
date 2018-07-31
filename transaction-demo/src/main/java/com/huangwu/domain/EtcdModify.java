package com.huangwu.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Package: com.huangwu.domain
 * @Author: huangwu
 * @Date: 2018/7/30 22:04
 * @Description:
 * @LastModify:
 */
@Data
public class EtcdModify {
    /**
     * id
     */
    private long etcdModifyId;
    /**
     * ip地址
     */
    private String etcdModifyAddr;
    /**
     * etcd key
     */
    private String etcdModifyKey;
    /**
     * etcd value
     */
    private String etcdModifyValue;
    /**
     * 修改者id
     */
    private long modifyUserid;
    /**
     * 修改时间
     */
    private Date modifiedTime;
    /**
     * 修改类型
     */
    private int modifyType;
    /**
     * 修改的超时时间
     */
    private long etcdModifyTtl;
    /**
     * 是否文件夹
     */
    private int isDir;
}

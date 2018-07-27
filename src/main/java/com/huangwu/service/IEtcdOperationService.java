package com.huangwu.service;

import com.huangwu.domain.EtcdModify;
import com.huangwu.domain.vo.EtcdVo;
import com.huangwu.etcd.EtcdNode;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * etcd操作service
 *
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/5/19 21:22
 * @Description:
 * @LastModify:
 */
public interface IEtcdOperationService {
    /**
     * 增删改操作入库
     *
     * @param data
     * @return
     */
    Integer insertOperation(EtcdModify data);

    EtcdNode queryEtcdNode(HttpServletRequest request, String path) throws Exception;

    EtcdNode recursiveQueryEtcdNode(HttpServletRequest request, String path) throws Exception;

    Long queryModifiedIndex(HttpServletRequest request, String path) throws Exception;

    EtcdNode updateEtcdNode(HttpServletRequest request, EtcdVo etcdVo) throws Exception;

    Date updateExpire(HttpServletRequest request, EtcdVo etcdVo) throws Exception;

    EtcdNode createEtcdDir(HttpServletRequest request, String path) throws Exception;

    EtcdNode createEtcd(HttpServletRequest request, EtcdVo etcdVo) throws Exception;

    void deleteEtcdDir(HttpServletRequest request, String path) throws Exception;

    void deleteEtcd(HttpServletRequest request, String path) throws Exception;

    Boolean registerEtcdAddress(HttpServletRequest request, String etcdAddress) throws Exception;

    Map<String, Object> queryRegisterHistory(HttpServletRequest request) throws Exception;

}

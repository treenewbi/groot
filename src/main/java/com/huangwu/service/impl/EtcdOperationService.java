package com.huangwu.service.impl;

import com.huangwu.event.observable.EtcdObservable;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.constant.EtcdModifyType;
import com.huangwu.domain.vo.EtcdVo;
import com.huangwu.etcd.EtcdActionResponse;
import com.huangwu.etcd.EtcdClient;
import com.huangwu.etcd.EtcdNode;
import com.huangwu.event.observable.EtcdObservable;
import com.huangwu.event.observer.EtcdModifyLogObserver;
import com.huangwu.exception.GlobalException;
import com.huangwu.mapper.EtcdModifyMapper;
import com.huangwu.domain.EtcdModify;
import com.huangwu.service.IEtcdOperationService;
import com.huangwu.util.PropertyUtil;
import com.huangwu.util.StringHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/5/19 21:44
 * @Description:
 * @LastModify:
 */
@Service
public class EtcdOperationService implements IEtcdOperationService {

    private static final String DEFAULT_CLIENT = "default";
    /**
     * 记录每个用户当前注册的etcd服务，当前没做用户管理，暂时按ip区分
     */
    private static ConcurrentHashMap<String, EtcdClient> etcdClientMap = new ConcurrentHashMap<>(32);
    /**
     * 记录每个用户的etcd服务注册历史，按ip区分
     */
    private static ConcurrentHashMap<String, CopyOnWriteArraySet<String>> etcdClientRegisterHistory = new ConcurrentHashMap<>(16);

    private static final EtcdObservable etcdObservable = new EtcdObservable("EtcdOperation");

    static {
        // 注册观察者对象
        EtcdModifyLogObserver observer = new EtcdModifyLogObserver(etcdObservable);
        // 注册默认etcd客户端
        EtcdClient defaultClient = new EtcdClient(StringHelper.buildEtcdUrl(PropertyUtil.getProperty("etcd.defaultAddress")));
        etcdClientMap.put(DEFAULT_CLIENT, defaultClient);
    }

    /**
     * 获取当前ip的注册地址
     * key为ip，同一台主机不会同时发生注册和查询的请求，此方法不加锁
     *
     * @param requestIp
     * @return
     */
    private EtcdClient getClient(String requestIp) {
        EtcdClient client = etcdClientMap.get(requestIp);
        if (client == null) {
            client = etcdClientMap.get(DEFAULT_CLIENT);
        }
        return client;
    }

    /**
     * 注册地址，如果已经存在则切换新的地址
     *
     * @param requestIp
     * @param client
     * @return
     */
    private synchronized EtcdClient putClient(String requestIp, EtcdClient client) {
        etcdClientMap.put(requestIp, client);
        putEtcdClientRegisterHistory(requestIp, client.getUrl());
        EtcdClient etcd = etcdClientMap.get(requestIp);
        return etcd;
    }

    /**
     * 添加历史访问记录
     *
     * @param requestIp
     * @param etcdAddress
     */
    private void putEtcdClientRegisterHistory(String requestIp, String etcdAddress) {
        if (etcdClientRegisterHistory.get(requestIp) == null) {
            CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
            set.add(etcdAddress);
            etcdClientRegisterHistory.put(requestIp, set);
        } else {
            CopyOnWriteArraySet<String> set = etcdClientRegisterHistory.get(requestIp);
            set.add(etcdAddress);
        }
    }

    /**
     * 查询当前ip的注册历史
     *
     * @param requestIp
     * @return
     */
    private List<String> getEtcdClientRegisterHistoryByIp(String requestIp) {
        CopyOnWriteArraySet<String> set = etcdClientRegisterHistory.get(requestIp);
        List<String> hisAddress = new ArrayList<>();
        if (set != null && set.size() > 0) {
            String[] array = {};
            array = set.toArray(array);
            for (String address : array) {
                hisAddress.add(address);
            }
        }
        if (!hisAddress.contains(PropertyUtil.getProperty("etcd.defaultAddress"))) {
            hisAddress.add(PropertyUtil.getProperty("etcd.defaultAddress"));
        }
        return hisAddress;
    }

    /**
     * 查询当前ip正在注册的etcd
     *
     * @param requestIp
     * @return
     */
    private String getEtcdClientRegisterCurrentByIp(String requestIp) {
        return getClient(requestIp).getUrl();
    }

    @Resource
    private EtcdModifyMapper etcdModifyMapper;

    @Override
    public Integer insertOperation(EtcdModify data) {
        return etcdModifyMapper.insertOperation(data);
    }

    @Override
    public EtcdNode queryEtcdNode(HttpServletRequest request, String path) throws Exception {
        EtcdClient client = getClient(request.getRemoteAddr());
        EtcdActionResponse response = client.get(path);
        return response.getNode();
    }

    @Override
    public EtcdNode recursiveQueryEtcdNode(HttpServletRequest request, String path) throws Exception {
        EtcdClient client = getClient(request.getRemoteAddr());
        EtcdActionResponse response = client.getDir(path, true);
        return response.getNode();
    }

    @Override
    public Long queryModifiedIndex(HttpServletRequest request, String path) throws Exception {
        EtcdNode node = queryEtcdNode(request, path);
        return node.getModifiedIndex();
    }

    @Override
    public EtcdNode updateEtcdNode(HttpServletRequest request, EtcdVo etcdVo) throws Exception {
        EtcdClient client = getClient(request.getRemoteAddr());
        EtcdActionResponse response = client.update(etcdVo.getPath(), etcdVo.getValue(), etcdVo.getTtl());
        EtcdModify updateModify = new EtcdModify(client.getUrl(), etcdVo.getPath(), etcdVo.getValue(), etcdVo.getTtl(), etcdVo.isDir(), 123456L, EtcdModifyType.UPDATE.getValue());
        etcdObservable.pushEventDataToObservers(updateModify);
        return response.getNode();
    }

    @Override
    public Date updateExpire(HttpServletRequest request, EtcdVo etcdVo) throws Exception {
        EtcdClient client = getClient(request.getRemoteAddr());
        EtcdActionResponse response = client.updateExpire(etcdVo.getPath(), etcdVo.getTtl(), true);
        EtcdModify updateModify = new EtcdModify(client.getUrl(), etcdVo.getPath(), etcdVo.getValue(), etcdVo.getTtl(), etcdVo.isDir(), 123456L, EtcdModifyType.UPDATE.getValue());
        etcdObservable.pushEventDataToObservers(updateModify);
        return response.getNode().getExpiration();
    }

    @Override
    public EtcdNode createEtcdDir(HttpServletRequest request, String path) throws Exception {
        if (checkExist(request,path))
            throw new GlobalException(ErrorCode.ETCD_PATH_EXIST.getCode(),ErrorCode.ETCD_PATH_EXIST.getMessage());
        EtcdClient client = getClient(request.getRemoteAddr());
        EtcdActionResponse response = client.putDir(path);
        EtcdModify updateModify = new EtcdModify(client.getUrl(), path, null, 0L, true, 123456L, EtcdModifyType.INSERT.getValue());
        etcdObservable.pushEventDataToObservers(updateModify);
        return response.getNode();
    }

    @Override
    public EtcdNode createEtcd(HttpServletRequest request, EtcdVo etcdVo) throws Exception {
        if (checkExist(request,etcdVo.getPath()))
            throw new GlobalException(ErrorCode.ETCD_PATH_EXIST.getCode(),ErrorCode.ETCD_PATH_EXIST.getMessage());
        EtcdClient client = getClient(request.getRemoteAddr());
        EtcdActionResponse response = client.put(etcdVo.getPath(), etcdVo.getValue(), etcdVo.getTtl());
        EtcdModify updateModify = new EtcdModify(client.getUrl(), etcdVo.getPath(), etcdVo.getValue(), etcdVo.getTtl(), false, 123456L, EtcdModifyType.INSERT.getValue());
        etcdObservable.pushEventDataToObservers(updateModify);
        return response.getNode();
    }

    @Override
    public void deleteEtcdDir(HttpServletRequest request, String path) throws Exception {
        EtcdClient client = getClient(request.getRemoteAddr());
        client.deleteDir(path);
        EtcdModify updateModify = new EtcdModify(client.getUrl(), path, null, null, true, 123456L, EtcdModifyType.DELETE.getValue());
        etcdObservable.pushEventDataToObservers(updateModify);
    }

    @Override
    public void deleteEtcd(HttpServletRequest request, String path) throws Exception {
        EtcdClient client = getClient(request.getRemoteAddr());
        client.deleteDir(path);
        EtcdModify updateModify = new EtcdModify(client.getUrl(), path, null, null, false, 123456L, EtcdModifyType.DELETE.getValue());
        etcdObservable.pushEventDataToObservers(updateModify);
    }

    @Override
    public Boolean registerEtcdAddress(HttpServletRequest request, String etcdAddress) throws Exception {
        String requestIp = request.getRemoteAddr();
        EtcdClient client = getClient(requestIp);
        // 判断是否为默认地址
        if (PropertyUtil.getProperty("etcd.defaultAddress").equals(etcdAddress)) {
            //判断缓存是否为默认地址，如果是则说明注册了其他地址，此时需要切换回默认地址
            if (!client.getUrl().equals(etcdAddress)) {
                etcdClientMap.remove(requestIp);
            }
            return true;
        }
        client = new EtcdClient(new String[]{StringHelper.buildEtcdUrl(etcdAddress)});
        EtcdActionResponse response = client.getStats("self");
        // 先校验当前注册的client地址是否可用，如果可用则添加到缓存中
        if (response.isRegister()) {
            putClient(requestIp, client);
        }
        return response.isRegister();
    }

    @Override
    public Map<String, Object> queryRegisterHistory(HttpServletRequest request) throws Exception {
        String ip = request.getRemoteAddr();
        List<String> historyClients = getEtcdClientRegisterHistoryByIp(ip);
        String currentClient = getEtcdClientRegisterCurrentByIp(ip);
        Map<String, Object> result = new HashMap<>();
        result.put("historyClients", historyClients);
        result.put("currentClient", currentClient);
        return result;
    }

    /**
     * 检查path是否存在
     *
     * @param request
     * @param path
     * @return
     * @throws Exception
     */
    public boolean checkExist(HttpServletRequest request, String path) {
        EtcdNode node = null;
        try {
            node = queryEtcdNode(request, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node != null;
    }


}

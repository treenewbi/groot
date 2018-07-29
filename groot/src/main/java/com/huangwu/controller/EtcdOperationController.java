package com.huangwu.controller;

import com.huangwu.common.ErrorCode;
import com.huangwu.common.Result;
import com.huangwu.domain.vo.AddressVo;
import com.huangwu.domain.vo.EtcdVo;
import com.huangwu.etcd.EtcdNode;
import com.huangwu.service.IEtcdOperationService;
import com.huangwu.util.ValidatorUtil;
import com.huangwu.common.ErrorCode;
import com.huangwu.common.Result;
import com.huangwu.domain.vo.AddressVo;
import com.huangwu.domain.vo.EtcdVo;
import com.huangwu.etcd.EtcdNode;
import com.huangwu.exception.GlobalException;
import com.huangwu.service.IEtcdOperationService;
import com.huangwu.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * @Package: com.huangwu.controller
 * @Author: huangwu
 * @Date: 2018/5/19 13:13
 * @Description:
 * @LastModify:
 */
@RestController
@RequestMapping("/etcd")
public class EtcdOperationController {

    private Logger logger = LoggerFactory.getLogger(EtcdOperationController.class);

    @Resource
    private IEtcdOperationService etcdOperationService;


    /**
     * @api POST /etcd/queryEtcdNode 查询当前etcd服务器的目录,如果存在多个节点则返回nodes
     * @apiGroup etcd
     * @apiRequest String
     * @apiParam path String 需要查询的路径，root节点path为'/'
     * @apiExample String
     * {path:/groot}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam data.key String //etcd的key
     * @apiParam data.value String //key对应的value
     * @apiParam data.modifiedIndex Long //key修改时的索引
     * @apiParam data.createdIndex Long //key创建时索引
     * @apiParam data.ttl Long //超时时间(0代表永不超时)
     * @apiParam data.expiration Long //到期时间
     * @apiParam data.nodes Array //当前path下的所有子节点
     * @apiParam data.name String //名称
     * @apiParam data.dir Boolean //是否为文件夹(true:文件夹，false:非文件夹)
     * @apiParam data.nodesSize Int //所有子节点数量
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {
     * "key": "/",
     * "value": null,
     * "modifiedIndex": 0,
     * "createdIndex": 0,
     * "ttl": 0,
     * "expiration": null,
     * "nodes": [
     * {
     * "key": "/llbrain",
     * "value": null,
     * "modifiedIndex": 16,
     * "createdIndex": 16,
     * "ttl": 0,
     * "expiration": null,
     * "nodes": null,
     * "name": "llbrain",
     * "dir": true
     * },
     * {
     * "key": "/test",
     * "value": null,
     * "modifiedIndex": 4018,
     * "createdIndex": 4018,
     * "ttl": 0,
     * "expiration": null,
     * "nodes": null,
     * "name": "test",
     * "dir": true
     * }
     * ],
     * "name": "",
     * "dir": true,
     * "nodesSize":20
     * }
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/queryEtcdNode", method = RequestMethod.POST)
    public Result<EtcdNode> queryEtcdNode(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        EtcdNode node = etcdOperationService.queryEtcdNode(request, etcdVo.getPath());
        return Result.succees(node);
    }

    /**
     * @api POST /etcd/recursiveQueryEtcdNode 递归查询当前目录及其子节点
     * @apiGroup etcd
     * @apiRequest String
     * @apiParam path String 需要查询的路径，root节点path为'/'
     * @apiExample String
     * {path:/groot}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam data.key String //etcd的key
     * @apiParam data.value String //key对应的value
     * @apiParam data.modifiedIndex Long //key修改时的索引
     * @apiParam data.createdIndex Long //key创建时索引
     * @apiParam data.ttl Long //超时时间(0代表永不超时)
     * @apiParam data.expiration Long //到期时间
     * @apiParam data.nodes Array //当前path下的所有子节点
     * @apiParam data.name String //名称
     * @apiParam data.dir Boolean //是否为文件夹(true:文件夹，false:非文件夹)
     * @apiParam data.nodesSize Int //所有子节点数量
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {
     * "key": "/",
     * "value": null,
     * "modifiedIndex": 0,
     * "createdIndex": 0,
     * "ttl": 0,
     * "expiration": null,
     * "nodes": [
     * {
     * "key": "/llbrain",
     * "value": null,
     * "modifiedIndex": 16,
     * "createdIndex": 16,
     * "ttl": 0,
     * "expiration": null,
     * "nodes": null,
     * "name": "llbrain",
     * "dir": true
     * },
     * {
     * "key": "/test",
     * "value": null,
     * "modifiedIndex": 4018,
     * "createdIndex": 4018,
     * "ttl": 0,
     * "expiration": null,
     * "nodes": null,
     * "name": "test",
     * "dir": true
     * }
     * ],
     * "name": "",
     * "dir": true,
     * "nodesSize":20
     * }
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/recursiveQueryEtcdNode", method = RequestMethod.POST)
    public Result<EtcdNode> recursiveQueryEtcdNode(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        EtcdNode node = etcdOperationService.recursiveQueryEtcdNode(request, etcdVo.getPath());
        return Result.succees(node);
    }

    /**
     * @api POST /etcd/queryModifiedIndex 查询最新修改索引
     * @apiGroup etcd
     * @apiRequest String
     * @apiParam path String 需要查询的路径，root节点path为'/'
     * @apiExample String
     * {path:/groot}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam data.modifiedIndex Long //修改索引
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {modifiedIndex:123456}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/queryModifiedIndex", method = RequestMethod.POST)
    public Result<Long> queryModifiedIndex(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        Long modifiedIndex = etcdOperationService.queryModifiedIndex(request, etcdVo.getPath());
        return Result.succees(modifiedIndex);
    }

    /**
     * @api POST /etcd/updateEtcdNode 更新一个键值
     * @apiGroup etcd
     * @apiRequest String
     * @apiParam path String 路径
     * @apiParam value String 要更新的值
     * @apiParam ttl Long 超时时间，0代表永不超时
     * @apiParam isDir Boolean 是否为文件夹(true:文件夹，false:非文件夹)
     * @apiExample Json
     * {path:/groot,value:"新的value",ttl:2000}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam data
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/updateEtcdNode", method = RequestMethod.POST)
    public Result<EtcdNode> updateEtcdNode(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        EtcdNode node = etcdOperationService.updateEtcdNode(request, etcdVo);
        return Result.succees(node);
    }

    /**
     * @api POST /etcd/updateExpire 更新一个键的过期时间
     * @apiGroup etcd
     * @apiRequest Json
     * @apiParam path String 路径
     * @apiParam ttl Long 超时时间，0代表永不超时
     * @apiExample Json
     * {path:/groot,ttl:2000}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam data 失效时间
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {1526990123252}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/updateExpire", method = RequestMethod.POST)
    public Result<Date> updateExpire(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        Date expirationTime = etcdOperationService.updateExpire(request, etcdVo);
        return Result.succees(expirationTime);
    }

    /**
     * @api POST /etcd/createEtcdDir 创建一个目录
     * @apiGroup etcd
     * @apiRequest String
     * @apiParam path String 需要创建的目录path
     * @apiExample String
     * {path:/groot}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam data.modifiedIndex Long //修改索引
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/createEtcdDir", method = RequestMethod.POST)
    public Result<EtcdNode> createEtcdDir(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        EtcdNode node = etcdOperationService.createEtcdDir(request, etcdVo.getPath());
        return Result.succees(node);
    }

    /**
     * @api POST /etcd/createEtcd 创建一个键值
     * @apiGroup etcd
     * @apiRequest String
     * @apiParam path String 路径
     * @apiParam value String 要创建的值
     * @apiParam ttl Long 超时时间，0代表永不超时
     * @apiExample Json
     * {path:/groot,value:"新的value",ttl:2000}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiParam data
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/createEtcd", method = RequestMethod.POST)
    public Result<EtcdNode> createEtcd(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        EtcdNode node = etcdOperationService.createEtcd(request, etcdVo);
        return Result.succees(node);
    }

    /**
     * @api POST /etcd/deleteEtcdDir 删除一个目录
     * @apiGroup etcd
     * @apiRequest String
     * @apiParam path String 目录path
     * @apiExample String
     * {path:/groot}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/deleteEtcdDir", method = RequestMethod.POST)
    public Result<String> deleteEtcdDir(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        etcdOperationService.deleteEtcdDir(request, etcdVo.getPath());
        return Result.succees(null);
    }


    /**
     * @api POST /etcd/deleteEtcd 删除一个键值
     * @apiGroup etcd
     * @apiRequest String
     * @apiParam path String 键值path
     * @apiExample String
     * {path:/groot}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiExample json
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/deleteEtcd", method = RequestMethod.POST)
    public Result<String> deleteEtcd(HttpServletRequest request, @RequestBody @Valid EtcdVo etcdVo) throws Exception {
        etcdOperationService.deleteEtcd(request, etcdVo.getPath());
        return Result.succees(null);
    }

    /**
     * @api POST /etcd/register 注册一个etcd服务，如果不注册则取默认的地址http://172.26.12.61:4001
     * @apiGroup etcd
     * @apiRequest address
     * @apiExample String
     * {address:127.0.0.1:4000}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiExample json
     * @apiParam data 是否注册成功
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": {true}
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/register")
    public Result<Boolean> registerEtcdAddress(HttpServletRequest request, @RequestBody AddressVo addressVo) throws Exception {
        if (!ValidatorUtil.isAddress(addressVo.getAddress())) {
            return Result.error(ErrorCode.ERCD_ADDRESS_ERROR);
        }
        Boolean register = etcdOperationService.registerEtcdAddress(request, addressVo.getAddress());
        return Result.succees(register);
    }

    /**
     * @api POST /etcd/queryRegisterHistory 查询当前ip的etcd注册历史（当前历史数据存在缓存中，重启生效）
     * @apiGroup etcd
     * @apiExample Json
     * {}
     * @apiSuccess 200 Integer code 请求处理成功
     * @apiExample json
     * @apiParam data 当前ip历史注册的地址
     * {
     * "code": "200",
     * "message": "操作成功",
     * "data": [
     * "http://172.26.12.61:4001",
     * "http://172.32.1.61:4001"
     * ]
     * }
     * @apiError 400 Integer code 请求失败
     * @apiExample json {
     * result:{success:false,message:"请求失败","errorcode":"400"}
     * }
     */
    @RequestMapping(value = "/queryRegisterHistory")
    public Result<Map<String, Object>> queryRegisterHistory(HttpServletRequest request) throws Exception {
        Map<String, Object> map = etcdOperationService.queryRegisterHistory(request);
        return Result.succees(map);
    }

    @PostMapping(value = "/upload")
    public Result<String> uploadConfig(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new GlobalException(ErrorCode.ETCD_CONFIG_FILE_EMPTY);
        }
        //获取文件后缀
        String filename = file.getOriginalFilename();
        return null;
    }

}

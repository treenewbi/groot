package com.huangwu.service;

import com.huangwu.domain.EtcdModify;
import com.huangwu.mapper.EtcdModifyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/7/31 21:41
 * @Description:
 * @LastModify:
 */
@Service
public class EtcdModifyService {

    @Resource
    private EtcdModifyMapper etcdModifyMapper;

    /**
     * 添加etcd修改记录
     *
     * @param modify
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public int addEtcdModify(EtcdModify modify) {
        return etcdModifyMapper.insertOperation(modify);
    }

    /**
     * 表中modify_type字段最大长度是4，此处模拟超长
     *
     * @param modifyType
     * @return
     */
    public EtcdModify createEtcdModifyBean(int modifyType) {
        EtcdModify modify = new EtcdModify();
        modify.setEtcdModifyAddr("127.0.0.1");
        modify.setIsDir(1);
        modify.setModifyType(modifyType);
        modify.setEtcdModifyKey("helloworld");
        modify.setEtcdModifyTtl(1000);
        modify.setEtcdModifyValue("test value");
        modify.setModifyUserid(1001);
        modify.setModifiedTime(new Date());
        return modify;
    }
}

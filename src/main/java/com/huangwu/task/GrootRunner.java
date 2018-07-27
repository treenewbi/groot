package com.huangwu.task;

import com.huangwu.common.NamedThreadFactory;
import com.huangwu.event.EventDataQueueContext;
import com.huangwu.service.IEtcdOperationService;
import com.huangwu.common.NamedThreadFactory;
import com.huangwu.event.EventDataQueueContext;
import com.huangwu.service.IEtcdOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * groot启动时做的一些操作
 *
 * @Package: com.huangwu.task
 * @Author: huangwu
 * @Date: 2018/5/19 21:54
 * @Description:
 * @LastModify:
 */
@Component
@Order(value = 1)
public class GrootRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(GrootRunner.class);

    @Resource
    private IEtcdOperationService etcdOperationService;

    private static ExecutorService modifyPool = Executors.newSingleThreadExecutor(new NamedThreadFactory("ETCD-OPERATION"));


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("Start the GrootRunner...");
        collectEtcdModifyLog();
        logger.info("Start GrootRunner succeeded...");
    }

    /**
     * 收集etcd的操作日志
     */
    private void collectEtcdModifyLog() {
        logger.info("Start the EtcdModifyLogTask...");
        EtcdModifyLogTask task = new EtcdModifyLogTask(etcdOperationService, EventDataQueueContext.getEtcdModifiesQueue());
        modifyPool.execute(task);
        logger.info("Start log EtcdModifyLogTask succeeded...");
    }
}

package com.huangwu.task;

import com.huangwu.domain.EtcdModify;
import com.huangwu.service.IEtcdOperationService;
import com.huangwu.domain.EtcdModify;
import com.huangwu.service.IEtcdOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * etcd操作日志消费者
 *
 * @Package: com.huangwu.task
 * @Author: huangwu
 * @Date: 2018/5/19 20:38
 * @Description:
 * @LastModify:
 */
public class EtcdModifyLogTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(EtcdModifyLogTask.class);
    private IEtcdOperationService operationService;
    private BlockingQueue<EtcdModify> queue;

    public EtcdModifyLogTask(IEtcdOperationService operationService, BlockingQueue<EtcdModify> queue) {
        this.operationService = operationService;
        this.queue = queue;
    }


    @Override
    public void run() {
        while (true) {
            EtcdModify modify = null;
            try {
                modify = queue.take();
            } catch (InterruptedException e) {
                logger.error("execute etcdModifiesQueue.put() is in trouble:", e);
            }
            operationService.insertOperation(modify);
        }
    }
}

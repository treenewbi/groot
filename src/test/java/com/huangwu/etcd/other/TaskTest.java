package com.huangwu.etcd.other;

import com.huangwu.MainApplication;
import com.huangwu.domain.GrootUser;
import com.huangwu.mapper.UserMapper;
import com.huangwu.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Package: com.huangwu.etcd.othertest
 * @Author: huangwu
 * @Date: 2018/6/22 10:23
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class TaskTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;


    public static ExecutorService pool = Executors.newFixedThreadPool(5);
    public static CountDownLatch latch;
    public static AtomicInteger taskId = new AtomicInteger(0);

    @Test
    public void runTask() throws InterruptedException {
        List<Future<String>> futures = new ArrayList<>();
        latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            Future<String> future = pool.submit(new StateTask(latch, taskId));
            futures.add(future);
        }
        System.out.println("main thread do something...");
        TimeUnit.SECONDS.sleep(3);
        for (Future<String> future : futures) {
            if (!future.isDone()) {
                try {
                    System.out.println(future.get(3, TimeUnit.SECONDS));
                } catch (ExecutionException e) {
                    System.out.println("发起重试：" + future);
                    System.out.println("等待重试。。。。。然后终止");
                    TimeUnit.SECONDS.sleep(3);
                    future.cancel(true);
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    System.out.println("发起重试：" + future);
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("重试失败，终止任务");
                    future.cancel(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("发起重试：" + future);
                }
            } else {
                try {
                    System.out.println(future.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        latch.await();
    }

    @Test
    public void onWatch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                while (true) {
                try {
                    GrootUser user = userMapper.findUserByName("xiaoming");
                    if (user == null) {
                        TimeUnit.SECONDS.sleep(5);
                    } else {
                        runTask();
                    }
                } catch (Exception e) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
//                }
            }
        }).start();
        sleepForever();
    }

    public void sleepForever() {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

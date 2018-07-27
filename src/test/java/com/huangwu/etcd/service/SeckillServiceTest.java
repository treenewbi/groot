package com.huangwu.etcd.service;

import com.huangwu.domain.vo.SeckillUserVo;
import com.huangwu.service.ISeckillService;
import com.huangwu.MainApplication;
import com.huangwu.domain.vo.SeckillUserVo;
import com.huangwu.service.ISeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Package: com.huangwu.etcd.service
 * @Author: huangwu
 * @Date: 2018/6/3 9:43
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class SeckillServiceTest {

    @Autowired
    private ISeckillService seckillService;

    @Test
    public void testCreateVerifyCode() throws Exception {
        SeckillUserVo userVo = new SeckillUserVo();
        userVo.setUserName("zhansan");
        String verifyCode = seckillService.createVerifyCode(userVo,135999L);

    }
    @Test
    public void tets1() {
    }
}

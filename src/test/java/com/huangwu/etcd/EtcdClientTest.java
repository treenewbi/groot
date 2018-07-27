package com.huangwu.etcd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Package: com.huangwu.etcd
 * @Author: huangwu
 * @Date: 2018/5/19 13:47
 * @Description:
 * @LastModify:
 */
@RunWith(SpringRunner.class)
//@SpringBootTest
public class EtcdClientTest {

    static EtcdClient client = new EtcdClient(new String[]{"http://172.32.1.61:4001"});

    @Test
    public void testGetDir() throws Exception {
        String path = "/";
        EtcdActionResponse response = client.getDir(path, false);
        EtcdNode node = response.getNode();
        List<EtcdNode> nodes = node.getNodes();
        for (EtcdNode n : nodes) {
            System.out.println(n.toString());
        }
    }
}

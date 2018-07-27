package com.huangwu.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 基于httpClient的http请求工具
 *
 * @Package: huangwu.util
 * @Author: huangwu
 * @Date: 2018/5/18 10:58
 * @Description:
 * @LastModify:
 */
public class HttpHelper {

    private static HttpClient client = HttpClients.createDefault();

    /**
     * post请求发送json数据
     *
     * @param parameter
     * @param url
     * @return
     * @throws IOException
     */
    public static HttpResponse postJson(String parameter, String url) throws IOException {
        HttpPost method = new HttpPost(url);
        StringEntity entity = new StringEntity(parameter, Charset.forName("utf-8"));
        entity.setContentEncoding("utf-8");
        entity.setContentType("application/json");
        method.setEntity(entity);
        return client.execute(method);
    }

    public static void main(String[] args) throws IOException {
        String url = "http://172.26.12.100:8082/llbrain-euler-front-1.0/front/1.0/cluster_proxy/svc_url/";
        JSONObject o = new JSONObject();
        o.put("taskId", 322370586064191488L);
        o.put("svcName", "tensorboard");
        JSONObject json = new JSONObject();
        json.put("param", o);
        HttpResponse response = postJson(json.toString(), url);
    }


}

package com.huangwu.etcd;

import com.huangwu.common.json.JSONObject;
import com.huangwu.exception.EtcdException;
import com.huangwu.exception.JSONException;
import com.huangwu.common.json.JSONObject;
import com.huangwu.exception.EtcdException;
import com.huangwu.exception.JSONException;
import com.huangwu.util.CollectionHelper;
import com.huangwu.util.StringHelper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class EtcdUrl {
    String url;
    long lastAliveTime = 0;
    long lastCheckTime = 0;

    public long getCallTimes() {
        return callTimes;
    }

    synchronized public void setCallTimes(long callTimes) {
        this.callTimes = callTimes;
    }

    long callTimes;

    public boolean isAvailable() {
        return isAvailable;
    }

    public synchronized void setAvailable(boolean available) {
        isAvailable = available;
    }

    boolean isAvailable = true;

    public EtcdUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return url;
    }

    public synchronized String getUrl() {
        lastAliveTime = System.currentTimeMillis();
        return url;
    }

    public String getFormatUrl(String path, boolean wait, boolean recursive, boolean stream) {
        StringBuffer sb = new StringBuffer(this.url);
        sb.append("/v2/keys");
        sb.append(path);
        List<String> ls = new ArrayList<>();
        if (wait) {
            ls.add("wait=true");
        }
        if (recursive) {
            ls.add("recursive=true");
        }
        if (stream) {
            ls.add("stream=true");
        }
        String str = CollectionHelper.collectionToString(ls, "&");
        if (str.length() > 0) {
            sb.append("?" + str);
        }
        return sb.toString();
    }

    public String getStatstUrl(String path) {
        StringBuffer sb = new StringBuffer(this.url);
        sb.append("/v2/stats/");
        sb.append(path);
        return sb.toString();
    }

    public void checkAvailable() {
        lastCheckTime = System.currentTimeMillis();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        try {
            httpClient.execute(httpGet);
            isAvailable = true;
            lastAliveTime = System.currentTimeMillis();
        } catch (IOException e) {
            isAvailable = false;
        } finally {
            try {
                httpClient.close();
            } catch (Throwable e) {
            }
        }
    }
}

class EtcdUrls {
    EtcdUrl[] urls;
    int currentIndex;

    public EtcdUrls(String[] urls) {
        this.urls = new EtcdUrl[urls.length];
        for (int i = 0; i < urls.length; i++) {
            this.urls[i] = new EtcdUrl(urls[i]);
        }
        currentIndex = 0;
    }

    synchronized EtcdUrl next() {
        EtcdUrl url = urls[currentIndex];
        currentIndex++;
        if (currentIndex >= urls.length) {
            currentIndex = 0;
        }
        return url;
    }

    public EtcdUrl nextAvailable() {
        for (int i = 0; i < urls.length; i++) {
            EtcdUrl url = next();
            if (url.isAvailable()) {
                return url;
            }
        }
        return null;
    }

}

/**
 * ETCD Client
 */
public class EtcdClient {
    EtcdUrls urls;

    private String url;

    interface EtcdUrlStateChangeListener {
        void onUrlAvailableStateChange(EtcdUrl url, boolean prevAvailable);
    }

    CopyOnWriteArrayList<EtcdUrlStateChangeListener> listeners = new CopyOnWriteArrayList<>();

    class WatchThread extends Thread {

        public WatchThread() {
            super("etcd-backend");
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    watchUrls();
                } catch (Throwable e) {
                } finally {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }

        private void watchUrls() {
            long now = System.currentTimeMillis();
            for (EtcdUrl etcdUrl : urls.urls) {
                long t = now - etcdUrl.lastCheckTime;
                boolean available = etcdUrl.isAvailable;
                if ((!etcdUrl.isAvailable && t >= errorCheckInterval) || (etcdUrl.isAvailable && t >= normalCheckInterval)) {
                    etcdUrl.checkAvailable();
                    if (etcdUrl.isAvailable()) {
                        for (EtcdUrlStateChangeListener l : listeners) {
                            l.onUrlAvailableStateChange(etcdUrl, available);
                        }
                    }
                }
            }
        }

    }

    WatchThread watchThread;
    int errorCheckInterval = 20000;
    int normalCheckInterval = 300000;

    public EtcdClient(String... urls) {
        this.urls = new EtcdUrls(urls);
    }

    public int getErrorCheckInterval() {
        return errorCheckInterval;
    }

    public void setErrorCheckInterval(int errorCheckInterval) {
        this.errorCheckInterval = errorCheckInterval;
    }

    public int getNormalCheckInterval() {
        return normalCheckInterval;
    }

    public void setNormalCheckInterval(int normalCheckInterval) {
        this.normalCheckInterval = normalCheckInterval;
    }

    void addUrlStateChangeListener(EtcdUrlStateChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public synchronized WatchThread getWatchThread() {
        if (watchThread == null) {
            watchThread = new WatchThread();
            watchThread.start();
        }
        return watchThread;
    }

    public EtcdActionResponse httpGet(String path) throws IOException, EtcdException {
        EtcdUrl etcdUrl = getNextEtcdUrl();
        String url = etcdUrl.getFormatUrl(path, false, false, false);
        HttpGet httpGet = new HttpGet(url);
        return httpExecute(etcdUrl, httpGet);
    }

    public EtcdActionResponse httpStatsGet(String path) throws IOException, EtcdException {
        EtcdUrl etcdUrl = getNextEtcdUrl();
        String url = etcdUrl.getStatstUrl(path);
        HttpGet httpGet = new HttpGet(url);
        return httpExecute(etcdUrl, httpGet);
    }

    public EtcdActionResponse httpPut(String path, List<? extends NameValuePair> params) throws IOException, EtcdException {
        EtcdUrl etcdUrl = getNextEtcdUrl();
        String url = etcdUrl.getFormatUrl(path, false, false, false);
        HttpPut httpPut = new HttpPut(url);
        if (params != null) {
            UrlEncodedFormEntity se = new UrlEncodedFormEntity(params, "utf-8");
            httpPut.setEntity(se);
        }
        return httpExecute(etcdUrl, httpPut);
    }

    /**
     * 返回ip:port格式
     * @return
     */
    public String getUrl() {
        EtcdUrl etcdUrl = urls.nextAvailable();
        if (etcdUrl == null) {
            etcdUrl = urls.next();
        }
        this.url = etcdUrl.toString();
        return StringHelper.buildAddressUrl(url);
    }

    public EtcdUrl getNextEtcdUrl() throws EtcdException {
        EtcdUrl etcdUrl = urls.nextAvailable();
        if (etcdUrl == null) {
            etcdUrl = urls.next();
        }
        if (etcdUrl == null) {
            throw new EtcdException("etcd urls not configed.");
        }
        this.url = etcdUrl.toString();

        return etcdUrl;
    }

    public EtcdActionResponse httpPost(String path, List<? extends NameValuePair> params) throws IOException, EtcdException {
        EtcdUrl etcdUrl = getNextEtcdUrl();
        String url = etcdUrl.getFormatUrl(path, false, false, false);
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            UrlEncodedFormEntity se = new UrlEncodedFormEntity(params, "utf-8");
            httpPost.setEntity(se);
        }
        return httpExecute(etcdUrl, httpPost);
    }

    public EtcdActionResponse httpDelete(String path) throws IOException, EtcdException {
        EtcdUrl etcdUrl = getNextEtcdUrl();
        String url = etcdUrl.getFormatUrl(path, false, false, false);
        HttpDelete httpDelete = new HttpDelete(url);
        return httpExecute(etcdUrl, httpDelete);
    }

    public EtcdActionResponse httpExecute(EtcdUrl etcdUrl, HttpUriRequest httpUriRequest) throws IOException, EtcdException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().disableAutomaticRetries().build();
        try {
            CloseableHttpResponse response = httpClient.execute(httpUriRequest);
            InputStream is = response.getEntity().getContent();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            JSONObject jsonObject = new JSONObject(new String(os.toByteArray(), "utf-8"));
            EtcdActionResponse resp;
            if (response.getStatusLine().getStatusCode() >= 400) {
                throw new EtcdException(new EtcdErrorResponse(jsonObject));
            } else {
                resp = new EtcdActionResponse(jsonObject);
            }
            return resp;
        } catch (IOException e) {
            etcdUrl.setAvailable(false);
            throw e;
        } catch (JSONException e) {
            etcdUrl.setAvailable(false);
            throw new IOException(e);
        } catch (Exception e) {
            etcdUrl.setAvailable(false);
            throw e;
        } finally {
            try {
                httpClient.close();
            } catch (Throwable e) {
            }
        }
    }

    /**
     * 获取一个文件的内容
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse get(String path) throws IOException, EtcdException {
        return httpGet(path);
    }

    /**
     * 获取etcd的统计信息
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse getStats(String path) throws IOException, EtcdException {
        return httpStatsGet(path);
    }

    /**
     * 获取目录下的文件
     *
     * @param path      要获取的目录
     * @param recursive 是否获取子目录
     * @return 命令响应
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse getDir(String path, boolean recursive) throws IOException, EtcdException {
        if (recursive) {
            path += "?dir=true&recursive=true";
        }
        return httpGet(path);
    }

    /**
     * 设置一个键值
     *
     * @param path  键路径
     * @param value 键内容
     * @return 操作响应
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse put(String path, String value) throws IOException, EtcdException {
        return put(path, value, 0);
    }

    /**
     * 根据CAS设置值
     *
     * @param path     键路径
     * @param value    键内容
     * @param preValue 预设值
     * @return
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse compareAndSet(String path, String value, String preValue, long ttl) throws IOException, EtcdException {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("value", value));
        params.add(new BasicNameValuePair("prevValue", preValue));
        if (ttl > 0) {
            params.add(new BasicNameValuePair("ttl", Long.toString(ttl)));
        }
        return httpPut(path, params);
    }

    /**
     * 设置一个键值
     *
     * @param path  路径
     * @param value 值
     * @param ttl   多少秒后自动过期，0表示不过期
     * @return 操作响应
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse put(String path, String value, long ttl) throws IOException, EtcdException {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("value", value));
        if (ttl > 0) {
            params.add(new BasicNameValuePair("ttl", Long.toString(ttl)));
        }
        return httpPut(path, params);
    }

    /**
     * 重置一个键的过期时间
     *
     * @param path         键路径
     * @param ttl          过期时间，秒
     * @param notifyChange 是否触发watch事件
     * @return 操作响应
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse updateExpire(String path, long ttl, boolean notifyChange) throws IOException, EtcdException {
        List<BasicNameValuePair> params = new ArrayList<>();
        if (!notifyChange) {
            params.add(new BasicNameValuePair("refresh", "true"));
        }
        if (ttl > 0) {
            params.add(new BasicNameValuePair("ttl", Long.toString(ttl)));
            //确保createdIndex不会变化
            params.add(new BasicNameValuePair("prevExist", "true"));
        }
        return httpPut(path, params);
    }

    /**
     * 更新一个键值
     *
     * @param path  键路径
     * @param value 键值
     * @return 操作响应
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse update(String path, String value) throws IOException, EtcdException {
        return update(path, value, 0);
    }

    /**
     * 更新一个键值
     *
     * @param path  路径
     * @param value 值
     * @param ttl   多少秒后自动过期，0表示不过期
     * @return 操作结果
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse update(String path, String value, long ttl) throws IOException, EtcdException {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("value", value));
        params.add(new BasicNameValuePair("prevExist", "true"));
        if (ttl > 0) {
            params.add(new BasicNameValuePair("ttl", Long.toString(ttl)));
        }
        return httpPut(path, params);
    }

    /**
     * 创建一个目录
     *
     * @param path 键路径
     * @return 操作响应
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse putDir(String path) throws IOException, EtcdException {
        return httpPut(path + "?dir=true", null);
    }

    /**
     * 删除一个键值
     *
     * @param path 键路径
     * @return 操作响应
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse delete(String path) throws IOException, EtcdException {
        return httpDelete(path);
    }

    /**
     * 删除目录
     *
     * @param path 键路径
     * @return 操作响应
     * @throws IOException
     * @throws EtcdException
     */
    public EtcdActionResponse deleteDir(String path) throws IOException, EtcdException {
        return httpDelete(path + "?dir=true");
    }

    void innerDeleteDir(EtcdNode etcdNode) throws IOException, EtcdException {
        if (etcdNode.isDir()) {
            if (etcdNode.getNodes().size() > 0) {
                for (EtcdNode node : etcdNode.getNodes()) {
                    innerDeleteDir(node);
                }
            }
            deleteDir(etcdNode.getKey());
        } else {
            delete(etcdNode.getKey());
        }
    }

    /**
     * 强制删除目录，包含子目录的所有键值，慎用
     *
     * @param path 目录
     * @throws IOException
     * @throws EtcdException
     */
    public void forceDeleteDir(String path) throws IOException, EtcdException {
        try {
            httpDelete(path + "?dir=true");
        } catch (EtcdException e) {
            if (e.getErrorResponse().getErrorCode() == 108) {
                // 非空目录
                EtcdActionResponse response = getDir(path, true);
                innerDeleteDir(response.getNode());
            }
        }
    }

    /**
     * 上传一个文件
     *
     * @param key
     * @param file
     */
    public void upload(String key, File file) throws IOException, EtcdException {
        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), "UTF8"));
        try {
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            put(key, sb.toString());
        } finally {
            in.close();
        }
    }

    /**
     * 上传一个文件
     *
     * @param key
     * @param file
     */
    public void uploadDir(String key, File file) throws IOException, EtcdException {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (!f.isHidden()) {
                    if (f.getName().endsWith(".json") && f.isFile()) {
                        upload(key + "/" + f.getName().substring(0, f.getName().length() - 5), f);
                    } else if (f.isDirectory()) {
                        uploadDir(key + "/" + f.getName(), f);
                    }
                }
            }
        }
    }

    /**
     * 上传一个文件
     *
     * @param key
     * @param file
     */
    public void download(String key, File file) throws IOException, EtcdException {
        EtcdActionResponse r = get(key + ".json");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF8"));
        try {
            out.write(r.getNode().getValue());
        } finally {
            out.close();
        }
    }


    /**
     * 上传一个文件
     *
     * @param key
     * @param file
     */
    public void downloadDir(String key, File file) throws IOException, EtcdException {
        EtcdActionResponse r = getDir(key, true);
        save(file, r.getNode());
    }

    private void save(File file, EtcdNode node) throws IOException {
        if (node.isDir()) {
            if (node.getNodes() != null) {
                for (EtcdNode node1 : node.getNodes()) {
                    save(new File(file.getAbsolutePath() + "/" + node.getName()), node1);
                }
            }
        } else {
            file.mkdirs();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file.getAbsolutePath() + "/" + node.getName() + ".json"), "UTF8"));
            try {
                out.write(node.getValue());
            } finally {
                out.close();
            }
        }
    }
}

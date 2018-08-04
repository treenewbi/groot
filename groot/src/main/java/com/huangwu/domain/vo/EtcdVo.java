package com.huangwu.domain.vo;

import com.huangwu.validator.IsPath;
import com.huangwu.validator.IsPath;

/**
 * etcdVo
 *
 * @Package: com.huangwu.domain.vo
 * @Author: huangwu
 * @Date: 2018/5/22 9:19
 * @Description:
 * @LastModify:
 */
public class EtcdVo {

    @IsPath
    private String path;

    private String value;

    private Long ttl;

    private boolean isDir;

    public EtcdVo(String path, String value, Long ttl, boolean isDir) {
        this.path = path;
        this.value = value;
        this.isDir = isDir;
        if (ttl == null) {
            this.ttl = 0L;
        } else {
            this.ttl = ttl;
        }
    }

    public EtcdVo() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }
}

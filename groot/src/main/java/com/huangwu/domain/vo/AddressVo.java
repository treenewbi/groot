package com.huangwu.domain.vo;

/**
 * @Package: com.huangwu.domain.vo
 * @Author: huangwu
 * @Date: 2018/5/25 11:25
 * @Description:
 * @LastModify:
 */
public class AddressVo {
    /**
     * etcd地址（172.32.1.61:4001）
     */
    private String address;

    public AddressVo(String address) {
        this.address = address;
    }

    public AddressVo() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

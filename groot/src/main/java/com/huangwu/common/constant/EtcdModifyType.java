package com.huangwu.common.constant;

/**
 * @Package: com.huangwu.common.constant
 * @Author: huangwu
 * @Date: 2018/5/20 9:32
 * @Description:
 * @LastModify:
 */
public enum EtcdModifyType {
    INSERT("新增",1),
    DELETE("删除",2),
    UPDATE("修改",3)
    ;

    EtcdModifyType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

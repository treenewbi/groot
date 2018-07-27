package com.huangwu;

/**
 * @Package: com.huangwu
 * @Author: huangwu
 * @Date: 2018/7/16 13:16
 * @Description:
 * @LastModify:
 */
public enum OperationEnum {
    AddOperation("add"),
    DeleteOperation("delete"),
    UpdateOperation("update"),
    QueryOperation("query"),;

    private String type;

    OperationEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

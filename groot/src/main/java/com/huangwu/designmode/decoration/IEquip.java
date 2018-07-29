package com.huangwu.designmode.decoration;

/**
 * 装备接口
 *
 * @Package: com.huangwu.designmode.decoration
 * @Author: huangwu
 * @Date: 2018/7/11 17:19
 * @Description:
 * @LastModify:
 */
public interface IEquip {
    /**
     * 计算攻击力
     *
     * @return
     */
    int calculateAttacK();

    /**
     * 返回装备描述
     *
     * @return
     */
    String description();
}

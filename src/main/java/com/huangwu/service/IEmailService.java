package com.huangwu.service;

/**
 * 邮件服务
 *
 * @Package: com.huangwu.service
 * @Author: huangwu
 * @Date: 2018/5/29 13:19
 * @Description:
 * @LastModify:
 */
public interface IEmailService {
    /**
     * 发送简单格式邮件
     *
     * @param to
     * @param subject
     * @param content
     * @throws Exception
     */
    void sendSimpleEmail(String to, String subject, String content) throws Exception;

    /**
     * 发送HTML格式邮件
     *
     * @param to
     * @param subject
     * @param content
     * @throws Exception
     */
    void sendHtmlEmail(String to, String subject, String content) throws Exception;

    /**
     * 发送注册码HTML格式邮件
     *
     * @param to
     * @throws Exception
     */
    void sendRegisterHtmlEmail(String to) throws Exception;
}

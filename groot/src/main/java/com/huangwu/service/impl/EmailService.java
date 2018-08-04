package com.huangwu.service.impl;

import com.huangwu.common.ErrorCode;
import com.huangwu.domain.GrootUser;
import com.huangwu.exception.GlobalException;
import com.huangwu.mapper.UserMapper;
import com.huangwu.redis.EmailKey;
import com.huangwu.service.IEmailService;
import com.huangwu.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import static com.huangwu.redis.EmailKey.emailVerifyCodeKey;

/**
 * 邮件发送服务
 *
 * @Package: com.huangwu.service.impl
 * @Author: huangwu
 * @Date: 2018/5/29 13:21
 * @Description:
 * @LastModify:
 */
@Service
public class EmailService implements IEmailService {

    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final String REGISTER_SUBJECT = "groot系统注册码";

    @Autowired
    private JavaMailSender javaMailSender;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendSimpleEmail(String to, String subject, String content) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String content) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(username);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }

    @Override
    public void sendRegisterHtmlEmail(String to) throws Exception {
        GrootUser user = userMapper.queryUserByEmail(to);
        if (user != null) {
            throw new GlobalException(ErrorCode.EMAIL_ALREADY_REGISTERED);
        }
        String verifyCode = StringHelper.getFourRandom();
        //写入redis
        redisTemplate.opsForValue().set(emailVerifyCodeKey.realKey(to),verifyCode,emailVerifyCodeKey.expireSeconds());
        String content = createRegisterHtml(verifyCode);
        sendHtmlEmail(to, REGISTER_SUBJECT, content);
    }

    /**
     * 生成一个注册码邮件HTML模板
     *
     * @param verifyCode
     * @return
     */
    public String createRegisterHtml(String verifyCode) {
        StringBuilder content = new StringBuilder("<html>\n");
        content.append("<body>\n");
        content.append("<h3>欢迎注册ETCD可视化系统，本次注册码为：");
        content.append(verifyCode);
        content.append("，注册码5分钟内有效。</h3>\n");
        content.append("</body>\n");
        content.append("</html>");
        return content.toString();
    }
}

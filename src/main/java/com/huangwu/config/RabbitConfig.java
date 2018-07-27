package com.huangwu.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Package: com.huangwu.config
 * @Author: huangwu
 * @Date: 2018/6/8 8:36
 * @Description:
 * @LastModify:
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("hello");
    }

}

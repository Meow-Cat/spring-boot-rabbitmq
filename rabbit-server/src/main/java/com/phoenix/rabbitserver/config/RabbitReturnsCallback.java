package com.phoenix.rabbitserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author Phoenix
 */
@Slf4j
public class RabbitReturnsCallback implements RabbitTemplate.ReturnsCallback {

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息没有送到队列中");
        log.info("消息主体:{}",returnedMessage.getMessage());
        log.info("应答码:{}",returnedMessage.getReplyCode());
        log.info("描述:{}",returnedMessage.getReplyText());
        log.info("使用的交换机:{}",returnedMessage.getExchange());
        log.info("使用的路由键:{}",returnedMessage.getRoutingKey());
    }
}

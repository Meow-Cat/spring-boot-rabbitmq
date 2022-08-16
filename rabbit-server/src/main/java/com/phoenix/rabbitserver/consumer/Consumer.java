package com.phoenix.rabbitserver.consumer;

import com.phoenix.rabbitcommon.utils.redis.RedisService;
import com.phoenix.rabbitcommon.utils.spring.SpringUtils;
import com.phoenix.rabbitserver.utils.MessageUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

public interface Consumer {

    /**
     * 缓存工具类
     */
    final RedisService redisService = SpringUtils.getBean(RedisService.class);

    final MessageUtil messageUtil = SpringUtils.getBean(MessageUtil.class);

    /**
     * 重试次数最大上限
     */
    int MAX_ATTEMPTS = 3;

    /**
     * 消费消息
     * @param message 消息
     * @param channel 信道
     * @throws Exception
     */
    void consumption(Message message, Channel channel, @Payload String body, @Headers Map<String,Object> headers) throws Exception;

}

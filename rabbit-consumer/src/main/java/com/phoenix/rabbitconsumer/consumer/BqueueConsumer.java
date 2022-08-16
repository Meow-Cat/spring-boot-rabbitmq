package com.phoenix.rabbitconsumer.consumer;

import com.phoenix.rabbitcommon.utils.rabbit.RabbitConstant;
import com.phoenix.rabbitserver.consumer.Consumer;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author Phoenix
 */
@Component
@Slf4j
public class BqueueConsumer implements Consumer {

    @Override
    @RabbitListener(queues = RabbitConstant.TEST_B_QUEUE_NAME
            ,errorHandler = "messageErrorHandler")
    public void consumption(Message message, Channel channel, @Payload String body, @Headers Map<String,Object> headers) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String msgId = Consumer.messageUtil.getMsgId(headers);
        log.info("队列：{},消费成功：{},消息内容:{}",RabbitConstant.TEST_B_QUEUE_NAME, deliveryTag, new String(message.getBody()));
        int i = 1/0;
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        messageUtil.normalConsumer(msgId);
    }
}

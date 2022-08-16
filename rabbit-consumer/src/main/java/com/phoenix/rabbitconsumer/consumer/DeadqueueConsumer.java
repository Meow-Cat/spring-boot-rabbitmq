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
public class DeadqueueConsumer implements Consumer {

    @Override
    @RabbitListener(queues = RabbitConstant.TEST_DEAD_QUEUE_NAME,ackMode = "NONE")
    public void consumption(Message message, Channel channel, @Payload String body, @Headers Map<String,Object> headers) throws IOException {
        log.info("死信队列接受到的消息为：{}",new String(message.getBody()));
        String msgId = messageUtil.getMsgId(headers);
        messageUtil.deadConsumer(msgId);
    }
}

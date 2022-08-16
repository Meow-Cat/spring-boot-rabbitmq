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
public class CqueueConsumer implements Consumer {

    @Override
    @RabbitListener(queues = RabbitConstant.TEST_C_QUEUE_NAME,
            returnExceptions = "true")
    public void consumption(Message message, Channel channel, @Payload String body, @Headers Map<String,Object> headers) throws IOException {
            log.info("mq接受到的消息为：{}",new String(message.getBody()));
        String msgId = messageUtil.getMsgId(headers);
        int num = (int) redisService.getCacheObject(msgId);
        try {
            int i = 1/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            log.error("消息消费发生了错误，原因:{}",e.getMessage());
            if (num >= Consumer.MAX_ATTEMPTS){
                // 超出最大重试次数,放入死信队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                redisService.deleteObject(msgId);
            }else{
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
                redisService.setCacheObject(msgId,num+1);
            }
        }
    }
}

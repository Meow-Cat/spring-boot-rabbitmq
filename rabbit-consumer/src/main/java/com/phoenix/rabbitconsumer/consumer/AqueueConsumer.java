package com.phoenix.rabbitconsumer.consumer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.phoenix.rabbitauthorization.entity.SysAuthorization;
import com.phoenix.rabbitauthorization.service.SysAuthorizationService;
import com.phoenix.rabbitcommon.utils.rabbit.RabbitConstant;
import com.phoenix.rabbitserver.consumer.Consumer;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AqueueConsumer implements Consumer {

    @Autowired
    private SysAuthorizationService sysAuthorizationService;

    @Override
    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.TEST_A_QUEUE_NAME)
    public void consumption(Message message, Channel channel, @Payload String body, @Headers Map<String,Object> headers) throws IOException {
        log.info("队列{}接受到的消息为：{}",RabbitConstant.TEST_A_QUEUE_NAME,new String(message.getBody()));
        String msgId = messageUtil.getMsgId(headers);
        try {
            JSONObject jsonObject = JSON.parseObject(new String(message.getBody()));
            SysAuthorization sysAuthorization = jsonObject.to(SysAuthorization.class);
            sysAuthorizationService.insert(sysAuthorization);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            messageUtil.normalConsumer(msgId);
        }catch (Exception e){
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }
    }
}

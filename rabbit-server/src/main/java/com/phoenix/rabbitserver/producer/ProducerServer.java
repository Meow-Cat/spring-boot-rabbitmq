
package com.phoenix.rabbitserver.producer;

import com.phoenix.rabbitserver.utils.MessageUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerServer {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private MessageUtil messageUtil;

    /**
     * 发送消息
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息
     * @param data 提供消息id
     */
    public void convertAndSend(String exchange,String routingKey,Message message,CorrelationData data){
        messageUtil.init(exchange,routingKey, new String(message.getBody()),data.getId());
        template.convertAndSend(exchange,routingKey,message,data);
    }
}



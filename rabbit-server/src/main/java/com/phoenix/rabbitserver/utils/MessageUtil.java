package com.phoenix.rabbitserver.utils;

import com.phoenix.rabbitserver.entity.MessageModel;
import com.phoenix.rabbitserver.service.MessageService;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageUtil {

    @Autowired
    private MessageService messageService;

    /**
     * 获取消息id
     * @param map 消息headers
     * @return String
     */
    public String getMsgId(Map<String,Object> map){
        return (String) map.get("spring_returned_message_correlation");
    }

    /**
     * 获取消息id
     * @param message 消息
     * @return String
     */
    public String getMsgId(Message message){
        return (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
    }

    /**
     * 消息初始化
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息
     * @param msgId 消息id
     */
    public void init(String exchange,String routingKey,String message,String msgId){
        messageService.insert(MessageModel.init(msgId,exchange,routingKey,message));
    }

    /**
     * 将消息状态改为发送状态
     * @param msgId 消息id
     */
    public void send(String msgId){
        MessageModel messageModel = MessageModel.builder()
                .msgId(msgId)
                .build()
                .send();
        messageService.update(messageModel);
    }

    /**
     * 消息发送失败
     * @param msgId 消息id
     * @param code 失败原因
     */
    public void sendError(String msgId,String code){
        MessageModel messageModel = MessageModel.builder()
                .msgId(msgId)
                .build()
                .error(code);
        messageService.update(messageModel);
    }

    /**
     * 消息发送成功
     * @param msgId 消息id
     */
    public void sendSuccess(String msgId){
        MessageModel messageModel = MessageModel.builder()
                .msgId(msgId)
                .build()
                .success();
        messageService.update(messageModel);
    }

    /**
     * 正常消费
     * @param msgId 消息id
     */
    public void normalConsumer(String msgId){
        MessageModel messageModel = MessageModel.builder()
                .msgId(msgId)
                .build()
                .normalConsumer();
        messageService.update(messageModel);
    }

    /**
     * 非正常消费,进入死信队列
     * @param msgId 消息id
     */
    public void deadConsumer(String msgId){
        MessageModel messageModel = MessageModel.builder()
                .msgId(msgId)
                .build()
                .deadConsumer();
        messageService.update(messageModel);
    }

}

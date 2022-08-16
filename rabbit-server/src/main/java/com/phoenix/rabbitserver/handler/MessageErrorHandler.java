package com.phoenix.rabbitserver.handler;

import com.phoenix.rabbitserver.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rabbit异常监听器，在try-catch时失效
 * @author Phoenix
 */
@Component("messageErrorHandler")
@Slf4j
public class MessageErrorHandler implements RabbitListenerErrorHandler {

    @Autowired
    private MessageUtil messageUtil;

    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
        String msgId = messageUtil.getMsgId(amqpMessage);
        log.error("消息消费发生了错误，原因:{}",exception.getCause().getMessage());
        return msgId;
    }
}

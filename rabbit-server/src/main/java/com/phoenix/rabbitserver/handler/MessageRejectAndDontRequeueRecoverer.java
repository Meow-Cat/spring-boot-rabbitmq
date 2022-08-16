package com.phoenix.rabbitserver.handler;

import com.phoenix.rabbitserver.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息重试用尽后的处理 在try-catch时失效
 * @author Phoenix
 */
@Slf4j
@Component
public class MessageRejectAndDontRequeueRecoverer implements MessageRecoverer {

    @Autowired
    private MessageUtil messageUtil;

    @Override
    public void recover(Message message, Throwable cause) {
        log.error("重试");
    }
}

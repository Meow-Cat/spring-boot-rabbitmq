package com.phoenix.rabbitserver.config;

import com.phoenix.rabbitcommon.utils.spring.SpringUtils;
import com.phoenix.rabbitserver.utils.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author Phoenix
 */
@Slf4j
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {

    private final MessageUtil messageUtil = SpringUtils.getBean(MessageUtil.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack){
            log.info("消息已经到达Exchange:{}",correlationData);
            messageUtil.sendSuccess(correlationData.getId());
        }else{
            log.info("消息未到达Exchange:{}",cause);
            messageUtil.sendError(correlationData.getId(),cause);
        }
    }
}

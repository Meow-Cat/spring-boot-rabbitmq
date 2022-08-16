package com.phoenix.rabbitserver.config;

import com.phoenix.rabbitcommon.utils.rabbit.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ExchangeConfig {

    /**
     * 声明Topic交换机
     */
    @Bean
    static TopicExchange topicExchange(){
        return new TopicExchange(RabbitConstant.TEST_TOPIC_EXCHANGE_NAME,true,true,null);
    }

    /**
     * 声明fanout交换机
     */
    @Bean
    static FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitConstant.TEST_FANOUT_EXCHANGE_NAME,true,true,null);
    }


    @Bean
    static HeadersExchange headersExchange(){
        return new HeadersExchange(RabbitConstant.TEST_HEADERS_EXCHANGE_NAME,true,true,null);
    }

    @Bean
    static DirectExchange deadExchange(){
        return new DirectExchange(RabbitConstant.TEST_DEAD_EXCHANGE_NAME,true,true,null);
    }

}

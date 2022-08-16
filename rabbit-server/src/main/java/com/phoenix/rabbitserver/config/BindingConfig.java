package com.phoenix.rabbitserver.config;
import com.phoenix.rabbitcommon.utils.rabbit.RabbitConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class BindingConfig {

    /**
     * 将队列和topic交换绑定
     * @param a 队列
     * @param exchange 交换机
     */
    @Bean
    Binding topicBindingToA(Queue a,TopicExchange exchange){
        return BindingBuilder.bind(a).to(exchange).with(RabbitConstant.A_ROUTING_KEY);
    }
    @Bean
    Binding topicBindingToB(Queue b,TopicExchange exchange){
        return BindingBuilder.bind(b).to(exchange).with(RabbitConstant.B_ROUTING_KEY);
    }
    @Bean
    Binding topicBindingToC(Queue c,TopicExchange exchange){
        return BindingBuilder.bind(c).to(exchange).with(RabbitConstant.C_ROUTING_KEY);
    }


    /**
     * 将队列和fanout交换绑定
     * @param a 队列
     * @param exchange 交换机
     */
    @Bean
    Binding fanoutBindingToA(Queue a, FanoutExchange exchange) {
        return BindingBuilder.bind(a).to(exchange);
    }
    @Bean
    Binding fanoutBindingToB(Queue b, FanoutExchange exchange) {
        return BindingBuilder.bind(b).to(exchange);
    }


    /**
     * 将队列和topic交换绑定
     * @param a 队列
     * @param exchange 交换机
     */
    @Bean
    Binding headersBindingA(Queue a, HeadersExchange exchange) {
        Map<String,Object> map = new HashMap<>(1);
        map.put("First","A");
        return BindingBuilder.bind(a).to(exchange).whereAny(map).match();
    }
    @Bean
    Binding headersBindingB(Queue b, HeadersExchange exchange) {
        Map<String,Object> map = new HashMap<>(1);
        map.put("First","B");
        return BindingBuilder.bind(b).to(exchange).whereAny(map).match();
    }

    /**
     * 死信
     */
    @Bean
    Binding deadBinding(Queue dead,DirectExchange deadExchange){
        return BindingBuilder.bind(dead).to(deadExchange).with("test.dead.a");
    }



}

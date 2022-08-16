package com.phoenix.rabbitserver.config;

import com.phoenix.rabbitcommon.utils.rabbit.RabbitConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueueConfig {

    /**
     * 声明队列
     */
    @Bean("a")
    static Queue queueA(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",RabbitConstant.TEST_DEAD_EXCHANGE_NAME);
        map.put("x-dead-letter-routing-key","test.dead.a");
        return new Queue(RabbitConstant.TEST_A_QUEUE_NAME,true,false,true,map);
    }

    @Bean("b")
    static Queue queueB(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",RabbitConstant.TEST_DEAD_EXCHANGE_NAME);
        map.put("x-dead-letter-routing-key","test.dead.a");
        return new Queue(RabbitConstant.TEST_B_QUEUE_NAME,true,false,true,map);
    }

    @Bean("c")
    static Queue queueC(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange",RabbitConstant.TEST_DEAD_EXCHANGE_NAME);
        map.put("x-dead-letter-routing-key","test.dead.a");
        return new Queue(RabbitConstant.TEST_C_QUEUE_NAME,true,false,true,map);
    }

    @Bean("dead")
    static Queue deadQueue(){
        return new Queue(RabbitConstant.TEST_DEAD_QUEUE_NAME,true,false,true,null);
    }

}

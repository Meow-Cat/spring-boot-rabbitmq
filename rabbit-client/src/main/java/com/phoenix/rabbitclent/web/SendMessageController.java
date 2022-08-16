package com.phoenix.rabbitclent.web;

import com.alibaba.fastjson2.JSON;
import com.phoenix.rabbitauthorization.entity.SysAuthorization;
import com.phoenix.rabbitauthorization.service.SysAuthorizationService;
import com.phoenix.rabbitserver.producer.ProducerServer;
import com.phoenix.rabbitcommon.utils.rabbit.RabbitConstant;
import com.phoenix.rabbitcommon.utils.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/send")
@Slf4j
public class SendMessageController {

    @Autowired
    private ProducerServer producerServer;

    @Autowired
    private SysAuthorizationService sysAuthorizationService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/topic")
    public String topic(){
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        SysAuthorization authorization = new SysAuthorization();
        authorization.setCompanyName("hello");
        authorization.setAppId("1");
        authorization.setAccessKey("2");
        authorization.setCreateTime(new Date());
        authorization.setStatus(1);
        try {
            Message message = MessageBuilder
                    .withBody(JSON.toJSONString(authorization).getBytes(StandardCharsets.UTF_8))
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .build();


            producerServer.convertAndSend(RabbitConstant.TEST_TOPIC_EXCHANGE_NAME,"test.a.1",message,data);
        }catch (Exception e){
            log.error("异常",e);
        }
        return "成功";
    }

    @RequestMapping("/sendError")
    public String sendError(){
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        Map<String,String> map = new HashMap<>();
        map.put("key","hello");
        try {
            Message message = MessageBuilder
                    .withBody(JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8))
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .build();
            if (redisService.getCacheObject(data.getId()) == null){
                redisService.setCacheObject(data.getId(),0);
            }
            producerServer.convertAndSend(RabbitConstant.TEST_TOPIC_EXCHANGE_NAME,"test.c.1",message,data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "成功";
    }

    @RequestMapping("/direct")
    public String direct(){
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        Map<String,String> map = new HashMap<>();
        map.put("key","hello");
        try {
            Message message = MessageBuilder
                    .withBody(JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8))
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .build();
            producerServer.convertAndSend(RabbitConstant.TEST_TOPIC_EXCHANGE_NAME,"test.b.1",message,data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "成功";
    }

    @RequestMapping("/header")
    public String header(){
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        Map<String,String> map = new HashMap<>();
        map.put("key","hello");
        try {
            Message message = MessageBuilder
                    .withBody(JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8))
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                    .build();
            producerServer.convertAndSend(RabbitConstant.TEST_TOPIC_EXCHANGE_NAME,"test.b.1",message,data);
            //producerServer.convertAndSend("test","test.b.1",message,data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "成功";
    }
}

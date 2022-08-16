package com.phoenix.rabbitserver.entity;

import com.phoenix.rabbitcommon.utils.rabbit.MessageConstant;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Phoenix
 */

@Data
@Builder
public class MessageModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msgId;

    private String exchange;

    private String routingKey;

    private String message;

    private Integer status;

    private String code;

    private Date createDate;

    public static MessageModel init(String msgId,String exchange,String routingKey,String message){
        return MessageModel.builder()
                .msgId(msgId)
                .exchange(exchange)
                .routingKey(routingKey)
                .message(message)
                .status(MessageConstant.Status.INIT.status())
                .createDate(new Date())
                .build();
    }

    public MessageModel send(){
        this.status = MessageConstant.Status.SEND.status();
        return this;
    }

    public MessageModel success(){
        this.status = MessageConstant.Status.SUCCESS.status();
        this.code = "发送成功";
        return this;
    }

    public MessageModel error(String code){
        this.status = MessageConstant.Status.ERROR.status();
        this.code = code;
        return this;
    }

    public MessageModel normalConsumer(){
        this.status = MessageConstant.Status.NORMALCONSUMER.status();
        return this;
    }

    public MessageModel deadConsumer(){
        this.status = MessageConstant.Status.DEADCONSUMER.status();
        return this;
    }
}

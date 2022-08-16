package com.phoenix.rabbitserver.service.impl;

import com.phoenix.rabbitserver.entity.MessageModel;
import com.phoenix.rabbitserver.mapper.MessageMapper;
import com.phoenix.rabbitserver.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public void insert(MessageModel messageModel) {
        messageMapper.insert(messageModel);
    }

    @Override
    public void update(MessageModel messageModel) {
        messageMapper.update(messageModel);
    }
}

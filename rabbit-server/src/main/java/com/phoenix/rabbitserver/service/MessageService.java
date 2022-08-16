package com.phoenix.rabbitserver.service;

import com.phoenix.rabbitserver.entity.MessageModel;

public interface MessageService {

    void insert(MessageModel messageModel);

    void update(MessageModel messageModel);
}

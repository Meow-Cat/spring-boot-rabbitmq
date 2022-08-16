package com.phoenix.rabbitserver.mapper;

import com.phoenix.rabbitserver.entity.MessageModel;

public interface MessageMapper {

    void insert(MessageModel messageModel);

    void update(MessageModel messageModel);
}

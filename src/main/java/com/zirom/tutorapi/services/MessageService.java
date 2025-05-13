package com.zirom.tutorapi.services;

import com.zirom.tutorapi.domain.entities.messages.BaseMessage;

public interface MessageService {
    BaseMessage saveTextMessage(BaseMessage baseMessage, String content);
}

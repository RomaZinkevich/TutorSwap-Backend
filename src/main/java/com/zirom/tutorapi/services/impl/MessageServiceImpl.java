package com.zirom.tutorapi.services.impl;

import com.zirom.tutorapi.domain.entities.messages.BaseMessage;
import com.zirom.tutorapi.domain.entities.messages.TextMessage;
import com.zirom.tutorapi.repositories.BaseMessageRepository;
import com.zirom.tutorapi.repositories.TextMessageRepository;
import com.zirom.tutorapi.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final BaseMessageRepository baseMessageRepository;
    private final TextMessageRepository textMessageRepository;

    @Override
    public BaseMessage saveTextMessage(BaseMessage baseMessage, String content) {
        BaseMessage savedBaseMessage = baseMessageRepository.save(baseMessage);

        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);
        textMessage.setMessage(savedBaseMessage);
        textMessageRepository.save(textMessage);

        return savedBaseMessage;
    }
}

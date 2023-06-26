package com.instantrip.was.domain.message.service;

import com.instantrip.was.domain.message.entity.Message;
import com.instantrip.was.domain.message.exception.MessageNotFoundException;
import com.instantrip.was.domain.message.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Override
    public Message findMessageByMessageId(Long messageId) {
        Optional<Message> message = messageRepository.findById(messageId);

        if (message.isPresent())
            return message.get();
        else throw new MessageNotFoundException();
    }
}

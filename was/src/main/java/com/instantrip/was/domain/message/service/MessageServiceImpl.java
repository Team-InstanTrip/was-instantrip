package com.instantrip.was.domain.message.service;

import com.instantrip.was.domain.message.entity.Message;
import com.instantrip.was.domain.message.exception.MessageException;
import com.instantrip.was.domain.message.exception.MessageExceptionType;
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
        Optional<Message> foundMessage = messageRepository.findById(messageId);

        if (foundMessage.isPresent()) {
            Message message = foundMessage.get();

            // 만료 여부
            if (message.isExpired())
                throw new MessageException(MessageExceptionType.MESSAGE_EXPIRED);
            else
                return message;
        }

        // 존재하지 않는 메시지
        else throw new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND);
    }

    @Override
    public void addMessage(Message message) {
        // 만료 시간 설정
        message.calculateExpireTime();
        messageRepository.save(message);
    }
}

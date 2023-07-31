package com.instantrip.was.domain.message.service;

import com.instantrip.was.domain.message.entity.Message;

public interface MessageService {
    Message findMessageByMessageId(Long messageId);
    void addMessage(Message message);
    void likeMessage(Long messageId, Long userId);
    void deleteMessage(Long messageId, Long userId);
    Message updateMessage(Long messageId, Long userId, Message message);
}

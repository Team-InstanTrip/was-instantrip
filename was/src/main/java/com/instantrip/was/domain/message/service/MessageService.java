package com.instantrip.was.domain.message.service;

import com.instantrip.was.domain.message.entity.Message;

public interface MessageService {
    Message findMessageByMessageId(Long messageId);
    void addMessage(Message message);
    void likeMessage(Long MessageId, Long UserId);
}

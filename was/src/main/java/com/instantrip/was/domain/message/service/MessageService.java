package com.instantrip.was.domain.message.service;

import com.instantrip.was.domain.message.entity.Message;

import java.util.List;

public interface MessageService {
    Message findMessageByMessageId(Long messageId);
    void addMessage(Message message);
    void likeMessage(Long messageId, Long userId);
    void dislikeMessage(Long messageId, Long userId);
    void deleteMessage(Long messageId, Long userId);
    Message updateMessage(Long messageId, Long userId, Message message);
    List<Message> listNearbyMessage(Double lat, Double lon, Integer radius);
}

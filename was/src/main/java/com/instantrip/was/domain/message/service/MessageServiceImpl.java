package com.instantrip.was.domain.message.service;

import com.instantrip.was.domain.favorite.entity.Favorite;
import com.instantrip.was.domain.favorite.repository.FavoriteRepository;
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

    @Autowired
    FavoriteRepository favoriteRepository;

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

    @Override
    public void likeMessage(Long messageId, Long userId) {
        // 메시지 찾기
        Optional<Message> foundMessage = messageRepository.findById(messageId);
        if (!foundMessage.isPresent())
            throw new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND);

        // 좋아요 누른적 있는지?
        Optional<Favorite> foundFavorite = favoriteRepository.findByMessageIdAndUserId(messageId, userId);
        // 이미 좋아요 처리 되었습니다
        if (foundFavorite.isPresent())
            throw new MessageException(MessageExceptionType.MESSAGE_ALREADY_LIKED);

        // 좋아요 처리
        Message message = foundMessage.get();
        message.setLikes(message.getLikes() + 1);
        messageRepository.save(message);

        // favorite 등록
        Favorite favorite = new Favorite(userId, messageId);
        favoriteRepository.save(favorite);
    }
}

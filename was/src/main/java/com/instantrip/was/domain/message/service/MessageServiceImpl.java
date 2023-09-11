package com.instantrip.was.domain.message.service;

import com.instantrip.was.domain.dislike.entity.Dislike;
import com.instantrip.was.domain.dislike.repository.DislikeRepository;
import com.instantrip.was.domain.favorite.entity.Favorite;
import com.instantrip.was.domain.favorite.repository.FavoriteRepository;
import com.instantrip.was.domain.message.entity.Message;
import com.instantrip.was.domain.message.exception.MessageException;
import com.instantrip.was.domain.message.exception.MessageExceptionType;
import com.instantrip.was.domain.message.repository.MessageRepository;
import com.instantrip.was.domain.user.exception.UserException;
import com.instantrip.was.domain.user.exception.UserExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    DislikeRepository dislikeRepository;

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

        // 싫어요를 누른 적이 있으면, 싫어요 취소
        Optional<Dislike> foundDislike = dislikeRepository.findByMessageIdAndUserId(messageId, userId);
        if (foundDislike.isPresent()) {
            dislikeRepository.delete(foundDislike.get());
        }

        // 좋아요 처리
        Message message = foundMessage.get();
        message.setLikes(message.getLikes() + 1);
        messageRepository.save(message);

        // favorite 등록
        Favorite favorite = new Favorite(userId, messageId);
        favoriteRepository.save(favorite);
    }

    @Override
    public void dislikeMessage(Long messageId, Long userId) {
        // 메시지 찾기
        Optional<Message> foundMessage = messageRepository.findById(messageId);
        if (!foundMessage.isPresent())
            throw new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND);

        // 싫어요 누른적 있는지?
        Optional<Dislike> foundDislike = dislikeRepository.findByMessageIdAndUserId(messageId, userId);
        // 이미 싫어요 처리 되었습니다
        if (foundDislike.isPresent())
            throw new MessageException(MessageExceptionType.MESSAGE_ALREADY_DISLIKED);

        // 좋아요를 누른 적이 있으면, 좋아요 취소
        Optional<Favorite> foundFavorite = favoriteRepository.findByMessageIdAndUserId(messageId, userId);
        if (foundFavorite.isPresent()) {
            favoriteRepository.delete(foundFavorite.get());
        }

        // 좋아요 처리
        Message message = foundMessage.get();
        message.setDislikes(message.getDislikes() + 1);
        messageRepository.save(message);

        // favorite 등록
        Dislike dislike = new Dislike(userId, messageId);
        dislikeRepository.save(dislike);
    }

    @Override
    public void deleteMessage(Long messageId, Long userId) {
        // 메시지 찾기
        Optional<Message> foundMessage = messageRepository.findById(messageId);
        if (!foundMessage.isPresent())
            throw new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND);

        // 메시지 작성자인지
        // TODO 관리자권한일 경우도 가능하도록
        Message message = foundMessage.get();
        if (!message.getUserId().equals(userId)) {
            log.error("▶▶▶ 메시지 작성자 : {}", message.getUserId());
            log.error("▶▶▶ 삭제 요청자 : {}", userId);
            throw new UserException(UserExceptionType.USER_FORBIDDEN);
        }

        message.setActiveStatus(false);
        message.setStatus("DU"); // TODO 상태코드
        messageRepository.save(message);
    }

    @Override
    public Message updateMessage(Long messageId, Long userId, Message message) {
        Message originalMessage = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException(MessageExceptionType.MESSAGE_NOT_FOUND));

        // 만료 여부
        if (originalMessage.isExpired())
            throw new MessageException(MessageExceptionType.MESSAGE_EXPIRED);

        // 메시지 작성자인지
        if (!originalMessage.getUserId().equals(userId)) {
            log.error("▶▶▶ 메시지 작성자 : {}", originalMessage.getUserId());
            log.error("▶▶▶ 수정 요청자 : {}", userId);
            throw new UserException(UserExceptionType.USER_FORBIDDEN);
        }

        // field update
        if (message.getDuration() != null) {
            originalMessage.setDuration(message.getDuration());
            originalMessage.calculateExpireTime();
        }
        if (message.getContents() != null)
            originalMessage.setContents(message.getContents());
        if (message.getMessageType() != null)
            originalMessage.setMessageType(message.getMessageType());

        originalMessage.setStatus("ED"); // TODO 상태코드
        return messageRepository.save(originalMessage);
    }

    @Override
    public List<Message> listNearbyMessage(Double lat, Double lon, Integer radius) {
        List<Message> list = messageRepository.findNearbyMessages(lat, lon, radius);
        return list;
    }
}

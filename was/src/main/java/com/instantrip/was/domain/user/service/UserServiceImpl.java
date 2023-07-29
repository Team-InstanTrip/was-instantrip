package com.instantrip.was.domain.user.service;

import com.instantrip.was.domain.user.entity.User;
import com.instantrip.was.domain.user.exception.UserException;
import com.instantrip.was.domain.user.exception.UserExceptionType;
import com.instantrip.was.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(User user) {
        // DB에 정보 존재하는 고객인 경우
        Optional<User> findUser = userRepository.findByKakaoUserNumber(user.getKakaoUserNumber());
        if (findUser.isPresent()) {
            user.setUserId(findUser.get().getUserId());
            user.setActiveStatus(true);
            user.setJoinDate(new Timestamp(System.currentTimeMillis()));
        }
        userRepository.save(user);
    }

    @Override
    public User findUserByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserException(UserExceptionType.USER_NOT_FOUND);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserException(UserExceptionType.USER_NOT_FOUND);
        }
    }

    @Override
    public User login(User user) {
        Optional<User> foundUser = userRepository.findByKakaoUserNumberAndActiveStatus(user.getKakaoUserNumber(), true);

        // 이미 회원인 경우 로그인처리
        if (foundUser.isPresent()) {
            log.info("▶▶▶ 로그인 성공");
            return foundUser.get();
        }
        // 회원 아닌 경우 -> 회원가입 처리 필요
        else {
            log.info("▶▶▶ 회원 아님");
            throw new UserException(UserExceptionType.USER_NOT_FOUND);
        }
    }

    @Override
    public void modifyUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void removeUser(User user) {
        user.setActiveStatus(false);
        userRepository.save(user);
    }
}

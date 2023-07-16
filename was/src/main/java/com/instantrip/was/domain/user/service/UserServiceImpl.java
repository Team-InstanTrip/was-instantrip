package com.instantrip.was.domain.user.service;

import com.instantrip.was.domain.user.entity.User;
import com.instantrip.was.domain.user.exception.UserException;
import com.instantrip.was.domain.user.exception.UserExceptionType;
import com.instantrip.was.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(User user) {
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
    public void login(User user) {
        Optional<User> foundUser = userRepository.findByKakaoUserNumber(user.getKakaoUserNumber());

        // 이미 회원인 경우 로그인처리
        if (foundUser.isPresent()) {
            // TODO 로그인
        }
        // 회원 아닌 경우 -> 회원가입 처리 필요
        else {
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

package com.instantrip.was.domain.user.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.instantrip.was.domain.user.entity.User;
import com.instantrip.was.domain.user.exception.UserNotFoundException;
import com.instantrip.was.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

        if (user.isPresent())
            return user.get();
        else throw new UserNotFoundException();
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent())
            return user.get();
        else throw new UserNotFoundException();
    }

    @Override
    public boolean login(User user) {
        Optional<User> foundUser = userRepository.findByKakaoUserNumber(user.getKakaoUserNumber());

        // 이미 회원인 경우 로그인처리
        if (foundUser.isPresent()) {
            // TODO 로그인
        }
        // 회원 아닌 경우 회원가입 처리
        else {
            // TODO 클라이언트에서 닉네임 입력받아서 전송받기
            addUser(user);
        }
        return true;
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

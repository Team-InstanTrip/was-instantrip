package com.instantrip.was.domain.user.service;

import com.instantrip.was.domain.user.entity.User;
import com.instantrip.was.domain.user.exception.DuplicateUserException;
import com.instantrip.was.domain.user.exception.InvalidLoginInfoException;
import com.instantrip.was.domain.user.exception.UserNotFoundException;
import com.instantrip.was.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void addUser(User user) {
        // loginId 중복검사
        if (existsUserByLoginId(user.getLoginId()))
            throw new DuplicateUserException();
        // email 중복검사
        if (existsUserByEmail(user.getEmail())) {
            throw new DuplicateUserException();
        }

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
    public Optional<User> findUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent())
            return user.get();
        else throw new UserNotFoundException();
    }

    @Override
    public boolean existsUserByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Override
    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean login(User user) {
        Optional<User> resultUser = userRepository.findByLoginIdAndActiveStatus(user.getLoginId(), user.getActiveStatus());
        if (!resultUser.isPresent())
            throw new UserNotFoundException();

        if(resultUser.get().getLoginPw().equals(user.getLoginPw()))
            throw new InvalidLoginInfoException();

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

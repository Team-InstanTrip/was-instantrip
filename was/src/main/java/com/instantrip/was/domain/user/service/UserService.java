package com.instantrip.was.domain.user.service;

import com.instantrip.was.domain.user.entity.User;

import java.util.Optional;

public interface UserService {
    void addUser(User user);
    User findUserByUserId(Long userId);
    User findUserByEmail(String email);
    void login(User user);
    void modifyUser(User user);
    void removeUser(User user);
}

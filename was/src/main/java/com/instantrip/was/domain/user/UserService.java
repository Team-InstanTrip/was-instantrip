package com.instantrip.was.domain.user;

import java.util.Optional;

public interface UserService {
    void addUser(User user);
    Optional<User> findUserByUserId(Long userId);
    Optional<User> findUserByEmail(String email);
    void modifyUser(User user);
    void removeUser(User user);
}

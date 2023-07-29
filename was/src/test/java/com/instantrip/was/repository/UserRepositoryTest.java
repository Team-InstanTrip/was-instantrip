package com.instantrip.was.repository;

import com.instantrip.was.domain.user.entity.User;
import com.instantrip.was.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("User 등록")
    void userSaveTest() {
//        User user = new User();
//        user.setLoginId("taemin0718");
//        user.setLoginPw("password");
//        user.setEmail("taemin@shinee.com");
//        user.setUserName("태민이");
//
//        User target = userRepository.save(user);
//        assertEquals(user.getLoginId(), target.getLoginId());
    }
}

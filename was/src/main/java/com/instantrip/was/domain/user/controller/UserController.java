package com.instantrip.was.domain.user.controller;

import com.instantrip.was.domain.user.dto.UserRequest;
import com.instantrip.was.domain.user.dto.UserResponse;
import com.instantrip.was.domain.user.service.UserService;
import com.instantrip.was.domain.user.entity.User;
import com.instantrip.was.domain.user.exception.DuplicateUserException;
import com.instantrip.was.domain.user.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(path = "/{userId}")
    public UserResponse userDetails(@PathVariable Long userId) {
        User user = userService.findUserByUserId(userId);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        
        return userResponse;
    }

    @PostMapping(path = "/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public void userAdd(@RequestBody UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        userService.addUser(user);
    }

    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody UserRequest userRequest) {
        // TODO
    }

    @GetMapping("/check-email")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> checkEmailDuplicated(@RequestParam String email) {
        if (userService.existsUserByEmail(email))
            return ResponseEntity.status(HttpStatus.FOUND).body("이메일이 중복됩니다.");
        else
            return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/check-login-id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> checkLoginIdDuplicated(@RequestParam String loginId) {
        if (userService.existsUserByLoginId(loginId)) {
            return ResponseEntity.status(HttpStatus.FOUND).body("아이디가 중복됩니다.");
        } else {
            // TODO 익셉션 처리
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
}

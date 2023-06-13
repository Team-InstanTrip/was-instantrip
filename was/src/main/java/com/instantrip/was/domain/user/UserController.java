package com.instantrip.was.domain.user;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Optional<User> user = userService.findUserByUserId(userId);
        if (user.isPresent()) {
            UserResponse userResponse = modelMapper.map(user.get(), UserResponse.class);
            return userResponse;
        }
        else
            return null;
    }

    @PostMapping(path = "/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public void userAdd(@RequestBody UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        userService.addUser(user);
    }
}

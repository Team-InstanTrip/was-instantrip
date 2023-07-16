package com.instantrip.was.domain.user.controller;

import com.instantrip.was.domain.user.dto.UserRequest;
import com.instantrip.was.domain.user.dto.UserResponse;
import com.instantrip.was.domain.user.service.KakaoService;
import com.instantrip.was.domain.user.service.UserService;
import com.instantrip.was.domain.user.entity.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    KakaoService kakaoService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(path = "/oauth")
    public void kakaoOauth(@RequestParam String code) {
        // 카카오 인가코드 수신
        logger.info("code : {}", code);

        // access token 발급
        String accessToken = kakaoService.getKakaoAccessToken(code);
        User user = kakaoService.getKakaoUserInfo(accessToken);

        // 로그인 서비스 호출
        userService.login(user);
    }

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
}

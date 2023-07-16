package com.instantrip.was.domain.user.controller;

import com.instantrip.was.domain.user.dto.UserRequest;
import com.instantrip.was.domain.user.dto.UserResponse;
import com.instantrip.was.domain.user.exception.UserException;
import com.instantrip.was.domain.user.exception.UserExceptionType;
import com.instantrip.was.domain.user.service.KakaoService;
import com.instantrip.was.domain.user.service.UserService;
import com.instantrip.was.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    KakaoService kakaoService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(path = "/oauth")
    public void kakaoOauth(@RequestParam String code, HttpServletRequest req) throws IOException {
        // 카카오 인가코드 수신
        log.info("code : {}", code);

        // access token 발급
        String accessToken = kakaoService.getKakaoAccessToken(code);
        User user = kakaoService.getKakaoUserInfo(accessToken);

        log.info("▶▶▶ 로그인 요청");

        User loginUser = userService.login(user);

        // 로그인 성공
        req.getSession().invalidate();
        HttpSession session = req.getSession(true);

        session.setAttribute("userId", loginUser.getUserId());
        session.setAttribute("kakaoUserNumber", loginUser.getKakaoUserNumber());
        session.setMaxInactiveInterval(1800);
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

    @GetMapping(path = "/logout")
    public void logout(HttpServletRequest req) {
        log.info("▶▶▶ 로그아웃 요청");

        HttpSession session = req.getSession(false);
        if (session != null)
            session.invalidate();
    }

    @GetMapping(path = "/sessionTest")
    public void sessionTest(@SessionAttribute(name = "userId", required = false) Long userId) {
        if (userId == null) {
            log.error("유저아이디 없는데?..");
        }
        else {
            log.error("ㅇㅂㅇ: {}", userId);
        }
    }
}

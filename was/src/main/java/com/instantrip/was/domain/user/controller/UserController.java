package com.instantrip.was.domain.user.controller;

import com.instantrip.was.domain.user.dto.UserJoinRequest;
import com.instantrip.was.domain.user.dto.UserLoginResponse;
import com.instantrip.was.domain.user.dto.UserResponse;
import com.instantrip.was.domain.user.exception.UserException;
import com.instantrip.was.domain.user.exception.UserExceptionType;
import com.instantrip.was.domain.user.service.KakaoService;
import com.instantrip.was.domain.user.service.UserService;
import com.instantrip.was.domain.user.entity.User;
import com.instantrip.was.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(path = "/api/users")

@Tag(name = "회원 API", description = "회원 관련 기능 API")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    KakaoService kakaoService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "카카오 인증 후처리", description = "카카오인증 후 기존회원 로그인 처리 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "회원가입이 필요합니다. 여기서 받은 응답 data값 kakaoUserNumber(필수), email(선택)을 받아서, 닉네임 입력 화면으로 넘어가서 회원가입처리"),
    })
    @GetMapping(path = "/oauth")
    public BaseResponse<UserLoginResponse> kakaoOauth(@RequestParam String code, HttpServletRequest req) throws IOException {
        // 카카오 인가코드 수신
        log.info("code : {}", code);

        // access token 발급
        String accessToken = kakaoService.getKakaoAccessToken(code);
        User kakaoUserInfo = kakaoService.getKakaoUserInfo(accessToken);

        log.info("▶▶▶ 로그인 요청");

        User loginUser = userService.login(kakaoUserInfo);

        // 회원가입 필요한 경우
        if (loginUser == null) {
            UserLoginResponse response = modelMapper.map(kakaoUserInfo, UserLoginResponse.class);
            return new BaseResponse<UserLoginResponse>("404", HttpStatus.NOT_FOUND,
                    "회원가입이 필요합니다.", response);

        }

        // 로그인 성공
        req.getSession().invalidate();
        HttpSession session = req.getSession(true);

        session.setAttribute("userId", loginUser.getUserId());
        session.setAttribute("kakaoUserNumber", loginUser.getKakaoUserNumber());
        session.setAttribute("userName", loginUser.getUserName());
        session.setMaxInactiveInterval(1800);

        UserLoginResponse userLoginResponse = modelMapper.map(loginUser, UserLoginResponse.class);
        return new BaseResponse<UserLoginResponse>("200", HttpStatus.OK,
                "로그인에 성공했습니다.", userLoginResponse);
    }

    @Operation(summary = "회원정보 조회", description = "회원번호가 userId인 회원정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping(path = "/{userId}")
    public BaseResponse<UserResponse> userDetails(@PathVariable Long userId, HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        if (session == null)
            throw new UserException(UserExceptionType.USER_UNAUTHORIZED);

        // 본인만 정보 조회 가능
        Long sessionUserId = (Long) session.getAttribute("userId");
        if (!userId.equals(sessionUserId))
            throw new UserException(UserExceptionType.USER_UNAUTHORIZED);

        User user = userService.findUserByUserId(userId);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return new BaseResponse<UserResponse>("200", HttpStatus.OK, "조회 성공했습니다.", userResponse);
    }

    @Operation(summary = "회원가입", description = "카카오 인증 완료 후, 기존 회원이 아닌 경우 닉네임 입력 후 호출하는 회원가입 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @PostMapping(path = "/sign-up")
    @ResponseStatus(HttpStatus.OK)
    public void userAdd(@RequestBody UserJoinRequest userJoinRequest) {
        User user = modelMapper.map(userJoinRequest, User.class);
        userService.addUser(user);
    }

    @GetMapping(path = "/logout")
    public void logout(HttpServletRequest req) {
        log.info("▶▶▶ 로그아웃 요청");

        HttpSession session = req.getSession(false);
        if (session != null)
            session.invalidate();
    }

    @Operation(summary = "테스트용 유저 삭제 API")
    @GetMapping(path = "/delete")
    public BaseResponse<Void> deleteUserForTest(@RequestParam Long userId) {
        log.info("테스트용 유저 삭제 요청");
        userService.deleteUserForTest(userId);

        return new BaseResponse<>("200", HttpStatus.OK, "삭제됐어용", null);
    }

    @Hidden
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

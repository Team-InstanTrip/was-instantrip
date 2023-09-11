package com.instantrip.was.domain.message.controller;

import com.instantrip.was.domain.message.dto.MessageReactionResponse;
import com.instantrip.was.domain.message.exception.MessageException;
import com.instantrip.was.domain.message.service.MessageService;
import com.instantrip.was.domain.user.exception.UserException;
import com.instantrip.was.domain.user.exception.UserExceptionType;
import com.instantrip.was.global.exception.GlobalExceptionType;
import com.instantrip.was.global.response.BaseResponse;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/messages/react")

@Slf4j
@Tag(name = "메시지 반응 API", description = "메시지 좋아요/싫어요 반응에 관련된 API 입니다.")
public class MessageReactionController {
    @Autowired
    MessageService messageService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "메시지 좋아요 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "필수 입력값이 누락되었습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 메시지입니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다"),
            @ApiResponse(responseCode = "40", description = "이미 좋아요 눌렀습니다")
    })
    @PostMapping(path = "/like")
    public BaseResponse<MessageReactionResponse> likeMessage(HttpServletRequest req,
                         @RequestParam(required = true) Long messageId) {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            throw new UserException(UserExceptionType.USER_UNAUTHORIZED);

        if (messageId == null)
            throw new MessageException(GlobalExceptionType.MISSING_INPUT);

        messageService.likeMessage(messageId, userId);

        MessageReactionResponse response = new MessageReactionResponse(messageId);
        return new BaseResponse<MessageReactionResponse>("200",
                HttpStatus.OK, "좋아요 표시했습니다.", response);
    }

    @Operation(summary = "메시지 싫어요 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "필수 입력값이 누락되었습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 메시지입니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다"),
            @ApiResponse(responseCode = "41", description = "이미 싫어요 표시한 메시지입니다.")
    })
    @PostMapping(path = "/dislike")
    public BaseResponse<MessageReactionResponse> dislikeMessage(HttpServletRequest req,
                                       @RequestParam(required = true) Long messageId) {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            throw new UserException(UserExceptionType.USER_UNAUTHORIZED);

        if (messageId == null)
            throw new MessageException(GlobalExceptionType.MISSING_INPUT);

        // TODO 좋아요, 싫어요 둘다 누를 수 없도록 제어
        messageService.dislikeMessage(messageId, userId);

        MessageReactionResponse response = new MessageReactionResponse(messageId);
        return new BaseResponse<MessageReactionResponse>("200",
                HttpStatus.OK, "싫어요 표시했습니다.", response);
    }

}

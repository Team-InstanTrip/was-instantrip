package com.instantrip.was.domain.message.controller;

import com.instantrip.was.domain.message.dto.MessageRegisterRequest;
import com.instantrip.was.domain.message.dto.MessageRequest;
import com.instantrip.was.domain.message.dto.MessageResponse;
import com.instantrip.was.domain.message.entity.Message;
import com.instantrip.was.domain.message.repository.MessageRepository;
import com.instantrip.was.domain.message.service.MessageService;
import com.instantrip.was.domain.user.exception.UserException;
import com.instantrip.was.domain.user.exception.UserExceptionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/messages")

@Tag(name = "메시지 API", description = "메시지 관련 기능 API")
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "메시지 등록", description = "로그인이 된 경우에만 메시지 등록이 가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
    })
    @PostMapping(path = "/register")
    @ResponseStatus
    public ResponseEntity<MessageResponse> messageAdd(HttpServletRequest req, @RequestBody MessageRegisterRequest messageRequest) {
        // TODO
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            throw new UserException(UserExceptionType.USER_UNAUTHORIZED);


        logger.info("=== meesageRequest : {}", messageRequest.toString());
        Message message = modelMapper.map(messageRequest, Message.class);
        message.setUserId(userId);
        logger.info("=== message : {}", message.toString());
        messageService.addMessage(message);

        return ResponseEntity.ok()
                .body(modelMapper.map(message, MessageResponse.class));
    }

    @Operation(summary = "메시지 1개 조회", description = "messageId를 통해 메시지 1개의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "만료된 메시지입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 메시지입니다.")
    })
    @GetMapping(path = "/{messageId}")
    public MessageResponse messageDetails(@PathVariable Long messageId) {
        Message message = messageService.findMessageByMessageId(messageId);
        MessageResponse messageResponse = modelMapper.map(message, MessageResponse.class);

        return messageResponse;
    }
}

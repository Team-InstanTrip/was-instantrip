package com.instantrip.was.domain.message.controller;

import com.instantrip.was.domain.message.dto.MessageEditRequest;
import com.instantrip.was.domain.message.dto.MessageRegisterRequest;
import com.instantrip.was.domain.message.dto.MessageResponse;
import com.instantrip.was.domain.message.dto.NearbyMessageResponse;
import com.instantrip.was.domain.message.entity.Message;
import com.instantrip.was.domain.message.exception.MessageException;
import com.instantrip.was.domain.message.service.MessageService;
import com.instantrip.was.domain.user.exception.UserException;
import com.instantrip.was.domain.user.exception.UserExceptionType;
import com.instantrip.was.global.dto.BaseResponse;
import com.instantrip.was.global.exception.GlobalExceptionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/messages")

@Slf4j
@Tag(name = "메시지 API", description = "메시지 관련 기능 API")
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "메시지 등록", description = "로그인이 된 경우에만 메시지 등록이 가능합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
    })
    @PostMapping
    @ResponseStatus
    public ResponseEntity<MessageResponse> messageAdd(HttpServletRequest req, @RequestBody MessageRegisterRequest messageRequest) {
        // TODO
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            throw new UserException(UserExceptionType.USER_UNAUTHORIZED);


        log.info("=== meesageRequest : {}", messageRequest.toString());
        Message message = modelMapper.map(messageRequest, Message.class);
        message.setUserId(userId);
        log.info("=== message : {}", message.toString());
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

    @Operation(summary = "메시지 삭제", description = "메시지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 메시지입니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다. (메시지 작성자 OR 관리자만 삭제 가능)")
    })
    @DeleteMapping(path = "/{messageId}")
    public BaseResponse<String> deleteMessage(HttpServletRequest req, @PathVariable Long messageId) {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            throw new UserException(UserExceptionType.USER_UNAUTHORIZED);

        messageService.deleteMessage(messageId, userId);
        return BaseResponse.ok("삭제되었습니다.");
    }

    @Operation(summary = "메시지 수정", description = "메시지를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 메시지입니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요합니다."),
            @ApiResponse(responseCode = "403", description = "권한이 없습니다. (메시지 작성자만 가능)"),
            @ApiResponse(responseCode = "403", description = "만료된 메시지입니다.")
    })
    @PatchMapping(path = "/{messageId}")
    public BaseResponse<Message> editMessage(HttpServletRequest req, @RequestBody MessageEditRequest messageRequest,
                                            @PathVariable Long messageId) {
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null)
            throw new UserException(UserExceptionType.USER_UNAUTHORIZED);

        Message message = modelMapper.map(messageRequest, Message.class);
        Message editedMessage = messageService.updateMessage(messageId, userId, message);

        return BaseResponse.ok("수정되었습니다.", editedMessage);
    }

    @Operation(summary = "근방 메시지 목록 조회", description = "위도, 경도, 반경(m)를 입력받아 근방 메시지 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "00", description = "OK")
    })
    @GetMapping(path = "/nearby")
    public BaseResponse<NearbyMessageResponse> listNearbyMessages(@RequestParam Double lat, @RequestParam Double lon, @RequestParam Integer radius) {
        if (lat == null || lon == null || radius == null) {
            throw new MessageException(GlobalExceptionType.MISSING_INPUT);
        }

        log.info("▶▶▶ 주변 메시지 조회 요청");
        log.info("위도: {} | 경도: {} | 범위: {}", lat, lon, radius);

        List<Message> list = messageService.listNearbyMessage(lat, lon, radius);
        List<MessageResponse> resList = list.stream()
                .map(source -> modelMapper.map(source, MessageResponse.class))
                .collect(Collectors.toList());
        NearbyMessageResponse nearbyMessageResponse = new NearbyMessageResponse(resList);

        return BaseResponse.ok("조회 완료되었습니다.", nearbyMessageResponse);
    }
}

package com.instantrip.was.domain.message.controller;

import com.instantrip.was.domain.message.dto.MessageResponse;
import com.instantrip.was.domain.message.entity.Message;
import com.instantrip.was.domain.message.repository.MessageRepository;
import com.instantrip.was.domain.message.service.MessageService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/messages")
public class MessageController {
    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public MessageResponse messageDetails(@PathVariable Long messageId) {
        Message message = messageService.findMessageByMessageId(messageId);
        MessageResponse messageResponse = modelMapper.map(message, MessageResponse.class);

        return messageResponse;
    }
}

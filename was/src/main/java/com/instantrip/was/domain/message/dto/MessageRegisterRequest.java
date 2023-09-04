package com.instantrip.was.domain.message.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageRegisterRequest {
    Integer duration;
    String contents;
    String messageType;
    Double latitude;
    Double longitude;
}

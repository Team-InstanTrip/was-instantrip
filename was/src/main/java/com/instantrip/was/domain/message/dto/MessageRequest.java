package com.instantrip.was.domain.message.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private Long messageId;
    private Long userId;
    private Timestamp createTime;
    private Integer duration;
    private Timestamp expireTime;
    private String contents;
    private String messageType;
    private Boolean activeStatus;
    private Double latitude;
    private Double longitude;
    private Integer likes;
    private String status;
}

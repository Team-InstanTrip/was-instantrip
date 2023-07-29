package com.instantrip.was.domain.user.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private String userName;
    private String email;
    private String role;
    private Long kakaoUserNumber;
}

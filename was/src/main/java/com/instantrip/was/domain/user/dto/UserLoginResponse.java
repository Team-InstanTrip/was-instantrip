package com.instantrip.was.domain.user.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private Long userId;
    private String userName;
    private String email;
    private Timestamp joinDate;
    private Boolean activeStatus;
    private String role;
    private Long kakaoUserNumber;
}

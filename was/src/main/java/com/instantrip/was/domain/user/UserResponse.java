package com.instantrip.was.domain.user;

import lombok.*;

import java.sql.Timestamp;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String loginId;
    private String loginPw;
    private String userName;
    private String email;
    private Timestamp joinDate;
    private Boolean activeStatus;
    private String role;
}

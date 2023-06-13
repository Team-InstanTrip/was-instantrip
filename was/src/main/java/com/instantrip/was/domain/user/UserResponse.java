package com.instantrip.was.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter @Setter @ToString
public class UserResponse {
    private String loginId;
    private String loginPw;
    private String userName;
    private String email;
    private Timestamp joinDate;
    private Boolean activeStatus;
    private String role;
}

package com.instantrip.was.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String userName;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String email;
    private String role;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long kakaoUserNumber;
}

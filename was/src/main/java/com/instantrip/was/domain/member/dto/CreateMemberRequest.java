package com.instantrip.was.domain.member.dto;

import com.instantrip.was.domain.member.model.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequest {
    private String loginId;
    private String loginPassword;
    private String nickname;
    private String email;
    private List<MemberRole> role;
}

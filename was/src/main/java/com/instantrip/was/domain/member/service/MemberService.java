package com.instantrip.was.domain.member.service;

import com.instantrip.was.domain.auth.JwtProvider;
import com.instantrip.was.domain.member.dto.CreateMemberRequest;
import com.instantrip.was.domain.member.entity.Member;
import com.instantrip.was.domain.member.model.MemberRole;
import com.instantrip.was.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void createMember(CreateMemberRequest request) {
        StringBuilder roles = new StringBuilder();
        for (MemberRole role : request.getRole()) {
            roles.append(role.name()).append(" ");
        }

        Member member = Member.builder()
                .loginId(request.getLoginId())
                .loginPassword(passwordEncoder.encode(request.getLoginPassword()))
                .nickname(request.getNickname())
                .email(request.getEmail())
                .joinDate(new Date(System.currentTimeMillis()))
                .activeStatus(true)
                .role(roles.toString())
                .build();

        log.info("Member Entitiy : {}", member.toString());
        memberRepository.save(member);
    }
}

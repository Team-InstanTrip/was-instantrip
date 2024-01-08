package com.instantrip.was.domain.member.controller;

import com.instantrip.was.domain.member.dto.CreateMemberRequest;
import com.instantrip.was.domain.member.service.MemberService;
import com.instantrip.was.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public BaseResponse<Void> createMember(@RequestBody CreateMemberRequest request) {
        memberService.createMember(request);
        return BaseResponse.ok("회원가입 성공");
    }
}

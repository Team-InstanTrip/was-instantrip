package com.instantrip.was.domain.member.dto;

import com.instantrip.was.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class MemberDetails implements UserDetails {

    private final Member member;

    public final Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return member.getAuthorities();
    }

    @Override
    public String getPassword() {
        return member.getLoginPassword();
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return member.getActiveStatus();
    }

    @Override
    public boolean isAccountNonLocked() {
        return member.getActiveStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return member.getActiveStatus();
    }

    @Override
    public boolean isEnabled() {
        return member.getActiveStatus();
    }
}

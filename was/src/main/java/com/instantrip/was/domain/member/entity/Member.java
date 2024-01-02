package com.instantrip.was.domain.member.entity;

import com.instantrip.was.global.util.BooleanTFConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "MEMBER")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;
    private String loginPassword;
    private String nickname;
    private String email;
    private Date joinDate;
    @Convert(converter = BooleanTFConverter.class)
    private Boolean activeStatus;
    private String role;

    public List<SimpleGrantedAuthority> getAuthorities() {
        String[] auths = role.split(";");
        List<SimpleGrantedAuthority> authList = new ArrayList<>();

        return Arrays.stream(auths).map(item -> {
            return new SimpleGrantedAuthority(item);
        }).toList();
    }
}

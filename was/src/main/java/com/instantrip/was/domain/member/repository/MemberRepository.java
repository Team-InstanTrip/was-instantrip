package com.instantrip.was.domain.member.repository;

import com.instantrip.was.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Override
    Optional<Member> findById(Long aLong);
    Optional<Member> findByLoginId(String loginId);
}

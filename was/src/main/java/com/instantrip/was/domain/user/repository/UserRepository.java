package com.instantrip.was.domain.user.repository;

import com.instantrip.was.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByLoginId(String loginId);
    public Optional<User> findByLoginIdAndActiveStatus(String loginId, boolean activeStatus);
    public boolean existsByEmail(String email);
    public boolean existsByLoginId(String loginId);
}

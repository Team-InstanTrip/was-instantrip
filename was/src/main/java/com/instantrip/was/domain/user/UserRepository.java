package com.instantrip.was.domain.user;

import com.instantrip.was.domain.user.User;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByLoginId(String loginId);
    public boolean existsByEmail(String email);
    public boolean existsByLoginId(String loginId);
}

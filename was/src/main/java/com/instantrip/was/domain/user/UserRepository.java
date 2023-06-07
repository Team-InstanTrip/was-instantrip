package com.instantrip.was.domain.user;

import com.instantrip.was.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

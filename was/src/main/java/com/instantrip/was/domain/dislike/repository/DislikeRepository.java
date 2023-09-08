package com.instantrip.was.domain.dislike.repository;

import com.instantrip.was.domain.dislike.entity.Dislike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DislikeRepository extends JpaRepository<Dislike, Long> {
    Optional<Dislike> findByMessageIdAndUserId(Long messageId, Long userId);
}

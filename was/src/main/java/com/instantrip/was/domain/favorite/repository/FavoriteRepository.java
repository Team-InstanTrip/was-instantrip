package com.instantrip.was.domain.favorite.repository;

import com.instantrip.was.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    public Optional<Favorite> findByMessageIdAndUserId(Long messageId, Long userId);
}

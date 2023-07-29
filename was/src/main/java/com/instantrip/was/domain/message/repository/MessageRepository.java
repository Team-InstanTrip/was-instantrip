package com.instantrip.was.domain.message.repository;

import com.instantrip.was.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

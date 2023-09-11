package com.instantrip.was.domain.message.repository;

import com.instantrip.was.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(
            value = "SELECT * FROM Message WHERE     (6371 * acos(" +
                    "        cos(radians(:latitude)) * cos(radians(latitude)) *" +
                    "        cos(radians(longitude) - radians(:longitude)) +" +
                    "        sin(radians(:latitude)) * sin(radians(latitude))" +
                    "    )) <= :radius " +
                    "AND active_status = 'T'",
            nativeQuery = true
    )
    List<Message> findNearbyMessages(@Param("latitude") Double lat, @Param("longitude") Double lon, @Param("radius") Integer radius);
}

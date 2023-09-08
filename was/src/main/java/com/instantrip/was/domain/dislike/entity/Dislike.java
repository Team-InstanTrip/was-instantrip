package com.instantrip.was.domain.dislike.entity;

import com.instantrip.was.global.util.BooleanTFConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Table(name = "DISLIKE")
@DynamicInsert @DynamicUpdate
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dislikeId;

    private Timestamp createTime;
    @Convert(converter = BooleanTFConverter.class)
    private Boolean activeStatus;
    private Long userId;
    private Long messageId;

    public Dislike(Long userId, Long messageId) {
        this.userId = userId;
        this.messageId = messageId;
    }
}

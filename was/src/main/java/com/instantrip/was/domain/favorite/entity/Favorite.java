package com.instantrip.was.domain.favorite.entity;

import com.instantrip.was.global.util.BooleanTFConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Table(name = "FAVORITE")
@DynamicInsert @DynamicUpdate
@Getter @Setter @ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoriteId;

    private Long userId;
    private Long messageId;
    private Timestamp createTime;
    @Convert(converter = BooleanTFConverter.class)
    private Boolean activeStatus;

    public Favorite(Long userId, Long messageId) {
        this.userId = userId;
        this.messageId = messageId;
    }
}

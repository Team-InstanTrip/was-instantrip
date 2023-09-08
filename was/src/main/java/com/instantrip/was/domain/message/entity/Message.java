package com.instantrip.was.domain.message.entity;

import com.instantrip.was.global.util.BooleanTFConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Table(name = "MESSAGE")
@DynamicInsert
@DynamicUpdate
@Getter @Setter @ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(sequenceName = "MESSAGE_ID_SEQ", allocationSize = 1, name = "MESSAGE_ID_SEQ")
    private Long messageId;

    private Long userId;
    private Timestamp createTime;
    private Integer duration;
    private Timestamp expireTime;
    private String contents;
    private String messageType;
    @Convert(converter = BooleanTFConverter.class)
    private Boolean activeStatus;
    private Double latitude;
    private Double longitude;
    private Integer likes;
    private Integer dislikes;
    private String status;

    public void calculateExpireTime() {
        this.createTime = new Timestamp(System.currentTimeMillis());
        this.expireTime = new Timestamp(createTime.getTime() + duration * 1000 * 60);
    }

    public boolean isExpired() {
        return expireTime.getTime() - System.currentTimeMillis() < 0;
    }
}

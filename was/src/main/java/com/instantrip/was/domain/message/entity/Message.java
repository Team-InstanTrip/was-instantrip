package com.instantrip.was.domain.message.entity;

import com.instantrip.was.global.util.BooleanTFConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Table(name = "MESSAGE")
@DynamicInsert
@DynamicUpdate
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    private Integer like;
    private String status;

}

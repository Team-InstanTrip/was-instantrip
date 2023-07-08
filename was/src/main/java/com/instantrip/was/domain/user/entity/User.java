package com.instantrip.was.domain.user.entity;

import com.instantrip.was.global.util.BooleanTFConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@Table(name = "USER_")
@DynamicInsert
@DynamicUpdate
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator(sequenceName = "USER_ID_SEQ", allocationSize = 1, name = "USER_ID_SEQ")
    private Long userId;

    private String userName;
    private String email;
    private Timestamp joinDate;
    @Convert(converter = BooleanTFConverter.class)
    private Boolean activeStatus;
    private String role;
    private String kakaoUserNumber;
}

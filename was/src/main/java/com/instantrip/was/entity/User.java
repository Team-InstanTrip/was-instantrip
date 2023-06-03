package com.instantrip.was.entity;

import com.instantrip.was.BooleanTFConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "USER_")
@DynamicInsert
@Getter @Setter @ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator(sequenceName = "USER_ID_SEQ", allocationSize = 1, name = "USER_ID_SEQ")
    private Long userId;

    private String loginId;
    private String loginPw;
    private String userName;
    private String email;
    private Timestamp joinDate;
    @Convert(converter = BooleanTFConverter.class)
    private Boolean activeStatus;
    private String role;
}

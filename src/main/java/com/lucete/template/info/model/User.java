package com.lucete.template.info.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
//생성자 어노테이션
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(name = "google_id", length = 50, nullable = true)
    private String googleId;
    @Column(name = "naver_id", length = 50, nullable = true)
    private String naverId;
    @Column(length = 50, nullable = true)
    private String email;
    @Column(length = 20, nullable = false)
    private String phone;
    @Column(length = 10, nullable = false)
    private String password;
    @Column
    private Integer semester;
    @Column(name = "team_code")
    private Integer teamCode;
    @Column(name = "permission_code")
    private Integer permissionCode;
    @Column
    private Boolean attManager;
    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Date created;
    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private Date updated;
    @Column(name = "profile_message")
    private String profileMessage;
    @Column(name = "profile_image")
    private String profileImage;
}
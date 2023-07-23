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
    @Column(name = "provider", length = 50, nullable = true)
    private String provider;

    @Column(name = "provider_id", length = 50, nullable = true)
    private String providerId;
    @Column(length = 50, nullable = true)
    private String email;
    @Column(length = 20, nullable = false)
    private String phone;
    @Column(length = 10, nullable = false)
    private String team;
    @Column(length = 50, nullable = false)
    private String password;
    @Column
    private Boolean status;
    @Column
    private Integer semester;
    @Column(name = "team_code")
    private Integer teamCode;
    @Column
    private Integer permission;
    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private Date created;
    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private Date updated;
    @Column(name = "profile_message",length = 250, nullable = true)
    private String profileMessage;
    // User가 작성한 모든 Post
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;
}
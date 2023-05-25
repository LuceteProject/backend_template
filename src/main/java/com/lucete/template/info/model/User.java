package com.lucete.template.info.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    @Column
    private Integer team_code;
    @Column
    private Integer permission;
    @Column
    private Date created;
    @Column
    private Date updated;
    @Column(length = 250, nullable = true)
    private String profile_message;

}
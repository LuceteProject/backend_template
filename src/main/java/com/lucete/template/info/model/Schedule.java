package com.lucete.template.info.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private Date start;
    @Column
    private Date end;
    @Column
    @ColumnDefault("null")
    private Date alarm;
    @Column(name = "team_code")
    private Integer teamCode;
}

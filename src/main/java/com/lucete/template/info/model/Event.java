package com.lucete.template.info.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @Column
    private Date noticeDate;
    @Column
    private Integer team_code;
}

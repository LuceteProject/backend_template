package com.lucete.template.info.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer header;
    @Column
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column
    private Integer permission;
    @Column
    private Boolean is_notice;
    @Column
    private Date created;
    @Column
    private Date updated;
    // 추가된 컬럼들
    @Column
    @JoinColumn(name = "user_id")
    private Long author_id;
    @Column
    private String author_name;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
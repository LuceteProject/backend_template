package com.lucete.template.info.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private Date created;

    @Column
    private Date updated;

}

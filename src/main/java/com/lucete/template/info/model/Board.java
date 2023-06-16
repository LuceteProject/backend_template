package com.lucete.template.info.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false)
    private Integer category;
    @Column
    private Integer header;
    @Column(length = 20, nullable = false)
    private String name;
    @Column
    private String description;
    @Column
    private Integer permission;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Post> posts;
}

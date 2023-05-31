package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Integer header;
    private String title;
    private String content;
    private Integer permission;
    private Boolean is_notice;
    private Date created;
    private Date updated;
    private Long user_id;
    private String author_name;
    private Long board_id;

    // Getters and Setters
}

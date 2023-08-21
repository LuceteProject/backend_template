package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Integer team_code;
    private String title;
    private String content;
    private Integer permission;
    private Boolean is_notice;
    private Date created;
    private Date updated;
    private Long board_id;
    private Long user_id;

    // Getters and Setters
}

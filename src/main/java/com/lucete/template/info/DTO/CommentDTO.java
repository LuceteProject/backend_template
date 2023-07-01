package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private Long post_id;
    private Long user_id;
    private String content;
    private Date created;
    private Date updated;
    private Long parent;
    private Boolean isDeleted;

    /* Getters and Setters */
}

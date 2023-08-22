package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private Integer teamCode;
    private String title;
    private String content;
    private Integer permissionCode;
    private Boolean isNotice;
    private Date created;
    private Date updated;
    private Long boardId;
    private Long userId;

    // Getters and Setters
}

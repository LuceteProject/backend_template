package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDTO {
    private Long id;
    private Long userId;
    private String content;
    private Integer teamCode;
    private Boolean completed;
}

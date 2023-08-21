package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDTO {
    private Long id;
    private Long user_id;
    private String content;
    private Integer team_code;
    private Boolean completed;
}

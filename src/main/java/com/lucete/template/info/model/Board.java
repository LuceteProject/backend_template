package com.lucete.template.info.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
    private Long id;
    private Integer category;
    private String board_name;
    private String board_discription;
    private Integer permission;
    private Boolean is_notice;
}

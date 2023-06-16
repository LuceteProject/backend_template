package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardDTO {
    private Long id;
    private Integer category;
    private Integer header;
    private String name;
    private String description;
    private Integer permission;
    // Getters and Setters
}

package com.lucete.template.info.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@Getter
@Setter
@ToString
public class User {
    private String name;
    private String phone;
    private String team;
    private Integer year;
    private Integer active;
    private Date created;
    private Date updated;
}
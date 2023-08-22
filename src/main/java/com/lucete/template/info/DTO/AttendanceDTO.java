package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AttendanceDTO {
    private Long id;
    private Long userId;
    private int point;
    private Date date;
}

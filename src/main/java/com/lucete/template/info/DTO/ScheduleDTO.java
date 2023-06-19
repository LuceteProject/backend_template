package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ScheduleDTO {
    private Long id;
    private Long user_id;
    private String title;
    private String content;
    private Date start;
    private Date end;
    private Date alarm;
    private Integer teamCode;
}

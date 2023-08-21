package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String google_id;
    private String naver_id;
    private String email;
    private String phone;
    private String password;
    private Integer semester;
    private Integer team_code;
    private Integer permission_code;
    private Boolean att_manager;
    private Date created;
    private Date updated;
    private String profile_message;
    private String profile_image;
}

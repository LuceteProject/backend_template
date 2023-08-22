package com.lucete.template.info.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String googleId;
    private String naverId;
    private String email;
    private String phone;
    private String password;
    private Integer semester;
    private Integer teamCode;
    private Integer permissionCode;
    private Boolean attManager;
    private Date created;
    private Date updated;
    private String profileMessage;
    private String profileImage;
}

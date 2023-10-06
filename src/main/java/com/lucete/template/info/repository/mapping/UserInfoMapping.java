package com.lucete.template.info.repository.mapping;

public interface UserInfoMapping {
    Long getId();
    String getName();
    String getEmail();
    String getPhone();
    Integer getSemester();
    Integer getTeamCode();
    String getProfileMessage();
    String getProfileImage();
    Integer getPermissionCode();
}

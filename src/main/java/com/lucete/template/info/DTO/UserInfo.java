package com.lucete.template.info.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String name;
    private String provider;
    private String providerId;
    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @NotEmpty(message = "전화번호는 필수항목입니다.")

    private String phone;
    @NotEmpty(message = "소속 팀 입력은 필수항목입니다.")
    private String team;
    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;
    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;
    @NotEmpty(message = "기수 입력은 필수항목입니다.")
    private Integer semester;

    private String profileMessage;
}

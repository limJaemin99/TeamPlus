package org.teamplus.mvc.dto;

import lombok.*;

import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersDTO {
    private String userNo;
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._]{3,11}$",message = "ID - 첫글자 영문,숫자와 기호 ._ 포함 4~12 글자")
    private String id;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[`~!@#$%^&*()-_=+]).{4,24}$",message = "패스워드 - 영문자,기호,숫자를 반드시 1개 포함하여 4~24 글자")
    private String password;
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})?$",message = "이메일 - 이메일 형식이 올바르지 않습니다.")
    private String email;
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{2,}$",message = "닉네임 - 특수문자 사용 불가 (2글자 이상)")
    private String nickName;
    private String job;
    private String description;
    private String profileURL;
    private String sns;
}

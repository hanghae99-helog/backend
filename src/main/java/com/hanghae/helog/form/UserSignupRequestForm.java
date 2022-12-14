package com.hanghae.helog.form;

import com.hanghae.helog.dto.user.UserSignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserSignupRequestForm {
    @Length(min = 4, max = 10)
    @Pattern(regexp = "^[A-Z|a-z|0-9]{4,10}$", message = "영문자나 숫자를 사용하여 4-10자로 입력해주세요.")
    private String userId;

    private String name;

    @Length(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^&*+])[A-Za-z!@#$%^&*+]{8,20}$", message = "영문자와 특수문자(!@#$%^&*+)를 모두 포함하여 8-20자로 입력해주세요.")
    private String password;
    private String passwordCheck;

    public UserSignupRequestDto toDto() {
        return new UserSignupRequestDto(this);
    }
}

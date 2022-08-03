package com.hanghae.helog.dto.user;

import com.hanghae.helog.form.UserSignupRequestForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignupRequestDto {
    private String userId;
    private String name;
    private String password;
    private String passwordCheck;

    public UserSignupRequestDto(UserSignupRequestForm userSignupRequestForm) {
        this.userId = userSignupRequestForm.getUserId();
        this.name = userSignupRequestForm.getName();
        this.password = userSignupRequestForm.getPassword();
        this.passwordCheck = userSignupRequestForm.getPasswordCheck();
    }
}

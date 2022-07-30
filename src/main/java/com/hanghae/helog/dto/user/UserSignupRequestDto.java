package com.hanghae.helog.dto.user;

import com.hanghae.helog.form.UserSignupRequestForm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignupRequestDto {
    private String userId;
    private String email;
    private String password;
    private String passwordCheck;

    public UserSignupRequestDto(UserSignupRequestForm userSignupRequestForm) {
        this.userId = userSignupRequestForm.getUserId();
        this.email = userSignupRequestForm.getEmail();
        this.password = userSignupRequestForm.getPassword();
        this.passwordCheck = userSignupRequestForm.getPasswordCheck();
    }
}

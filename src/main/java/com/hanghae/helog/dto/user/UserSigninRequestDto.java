package com.hanghae.helog.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSigninRequestDto {
    private String userId;
    private String password;
}

package com.hanghae.helog.service;

import com.hanghae.helog.domain.User;
import com.hanghae.helog.dto.user.UserCheckExistingIdResponseDto;
import com.hanghae.helog.dto.user.UserSignupRequestDto;
import com.hanghae.helog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCheckExistingIdResponseDto checkExistingId(String userId) {
        boolean result = userRepository.existsById(userId);

        return new UserCheckExistingIdResponseDto(result);
    }

    private boolean checkPasswordPasswordCheck(UserSignupRequestDto userSignupRequestDto) {
        String password = userSignupRequestDto.getPassword();
        String passwordCheck = userSignupRequestDto.getPasswordCheck();

        if(password.equals(passwordCheck)) {
            return true;
        } else {
            return false;
        }
    }

    public void signUp(UserSignupRequestDto userSignupRequestDto) {
        if(!checkPasswordPasswordCheck(userSignupRequestDto)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        User newUser = User.builder()
                .id(userSignupRequestDto.getUserId())
                .email(userSignupRequestDto.getEmail())
                .password(passwordEncoder.encode(userSignupRequestDto.getPassword()))
                .build();

        userRepository.save(newUser);
    }
}

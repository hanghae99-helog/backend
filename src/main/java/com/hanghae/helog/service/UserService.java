package com.hanghae.helog.service;

import com.hanghae.helog.domain.User;
import com.hanghae.helog.dto.user.UserCheckExistingIdResponseDto;
import com.hanghae.helog.dto.user.UserSigninRequestDto;
import com.hanghae.helog.dto.user.UserSigninResponseDto;
import com.hanghae.helog.dto.user.UserSignupRequestDto;
import com.hanghae.helog.repository.UserRepository;
import com.hanghae.helog.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
                            .name(userSignupRequestDto.getName())
                            .password(passwordEncoder.encode(userSignupRequestDto.getPassword()))
                            .build();

        userRepository.save(newUser);
    }

    @Override
    public User loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다."));
    }

    public UserSigninResponseDto signIn(UserSigninRequestDto userSigninRequestDto) {
        String id = userSigninRequestDto.getUserId();
        String password = userSigninRequestDto.getPassword();

        User user = userRepository.findById(id).get();

        if(passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(user);

            return new UserSigninResponseDto().builder()
                    .userId(id)
                    .token(token)
                    .build();
        } else {
            throw new BadCredentialsException("로그인에 실패하였습니다.");
        }
    }
}

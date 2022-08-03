package com.hanghae.helog.controller;

import com.hanghae.helog.dto.user.UserCheckExistingIdResponseDto;
import com.hanghae.helog.dto.user.UserSigninRequestDto;
import com.hanghae.helog.dto.user.UserSigninResponseDto;
import com.hanghae.helog.form.UserSignupRequestForm;
import com.hanghae.helog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/api/signup/{userId}")
    public ResponseEntity<?> checkExistingId(@PathVariable String userId) {
        UserCheckExistingIdResponseDto userCheckExistingIdResponseDto = userService.checkExistingId(userId);

        return new ResponseEntity<>(userCheckExistingIdResponseDto, HttpStatus.OK);
    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequestForm userSignupRequestForm) {
        userService.signUp(userSignupRequestForm.toDto());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/signin")
    public ResponseEntity<?> signin(@RequestBody UserSigninRequestDto userSigninRequestDto) {
        UserSigninResponseDto userSigninResponseDto = userService.signIn(userSigninRequestDto);

        return new ResponseEntity<>(userSigninResponseDto, HttpStatus.OK);
    }
}

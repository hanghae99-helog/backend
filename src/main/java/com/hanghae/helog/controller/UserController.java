package com.hanghae.helog.controller;

import com.hanghae.helog.dto.user.UserCheckExistingIdResponseDto;
import com.hanghae.helog.form.UserSignupRequestForm;
import com.hanghae.helog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/api/signup/{userId}")
    public UserCheckExistingIdResponseDto checkExistingId(@PathVariable String userId) {
        return userService.checkExistingId(userId);
    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserSignupRequestForm userSignupRequestForm) {
        userService.signUp(userSignupRequestForm.toDto());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

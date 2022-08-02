package com.hanghae.helog.dto.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentRequestDto {
    //사용자의 아이디 닉네임? 내용

    private String Content;
}

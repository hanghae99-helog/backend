package com.hanghae.helog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PostEditRequestDto {

    private String title;       // 게시글 제목

    private String content;     // 게시글 내용

    private String thumbnail;   // 이미지 파일 저장 장소

    private String subTitle;    // 게시글 서브 제목

    private String url;        // 게시글 URL

}

package com.hanghae.helog.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostEditRequestDto {

    private String title;       // 게시글 제목

    private String viewContent;     // 게시글 내용(뷰)

    private String writingContent;     // 게시글 내용(수정)

    private String thumbnail;   // 이미지 파일 저장 장소

    private String subTitle;    // 게시글 서브 제목

    private String url;        // 게시글 URL

}

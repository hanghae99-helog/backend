package com.hanghae.helog.dto.post;

import com.hanghae.helog.domain.Post;
import lombok.Getter;

@Getter
public class AllPostResponseDto {

    private Long post_id;       // 게시글 ID
    private String title;       // 게시글 제목
    private String subTitle;    // 게시글 서브 제목
    private String createdAt;   // 작성 시간
    private Long userId;       // 유저 ID
    private int commentCount;   // 댓글 개수
    private String thumbnail;   // 이미지 파일 저장 장소
    private String url;         // 게시글 URL

    public AllPostResponseDto(Post post, int commentCount) {
        this.post_id = post.getPost_id();
        this.title = post.getTitle();
        this.subTitle = post.getSubTitle();
        this.createdAt = post.getCreatedAt();
        this.userId = post.getUser().getUser_id();
        this.commentCount = commentCount;
        this.thumbnail = post.getThumbnail();
        this.url = post.getUrl();
    }
}

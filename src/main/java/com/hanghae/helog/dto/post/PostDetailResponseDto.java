package com.hanghae.helog.dto.post;

import com.hanghae.helog.domain.Post;
import lombok.Getter;

@Getter
public class PostDetailResponseDto {

    private Long postId;           // 게시글 ID
    private String title;           // 게시글 제목
    private String viewContent;         // 게시글 내용(뷰)
    private String writingContent;      // 게시글 내용(수정)
    private String subTitle;        // 게시글 서브 제목
    private String createdAt;       // 작성 시간
    private String userId;           // 사용자 아이디
    private int commentCount;       // 댓글 개수
    private String thumbnail;       // 이미지 파일 저장 장소
    private String url;             // 게시글 URL


    public PostDetailResponseDto(Post post, int commentCount) {
        this.postId = post.getPost_id();
        this.title = post.getTitle();
        this.viewContent = post.getViewContent();
        this.writingContent = post.getWritingContent();
        this.subTitle = post.getSubTitle();
        this.createdAt = post.getCreatedAt();
        this.userId = post.getUser().getId();
        this.commentCount = commentCount;
        this.thumbnail = post.getThumbnail();
        this.url = post.getUrl();
    }

}

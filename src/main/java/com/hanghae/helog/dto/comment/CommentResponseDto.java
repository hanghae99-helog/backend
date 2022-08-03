package com.hanghae.helog.dto.comment;

import com.hanghae.helog.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private String user_id;
    private Long post_id;
    private String content;
    private String createdAt;

    public CommentResponseDto(Comment comment){
        this.user_id=comment.getUser().getId();
        this.post_id=comment.getPost().getPost_id();
        this.content=comment.getContent();
        this.createdAt=comment.getCreatedAt();
    }


}

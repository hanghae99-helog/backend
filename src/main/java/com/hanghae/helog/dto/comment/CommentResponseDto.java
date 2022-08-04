package com.hanghae.helog.dto.comment;

import com.hanghae.helog.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Getter
public class CommentResponseDto {
    private Long commentId;
    private String userId;
    private String createdAt;
    private String comment;

    public CommentResponseDto(Comment comment){
        this.commentId=comment.getComment_id();
        this.userId=comment.getUser().getId();
        this.createdAt=comment.getCreatedAt();
        this.comment=comment.getContent();
    }
}

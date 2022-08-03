package com.hanghae.helog.dto.comment;

import com.hanghae.helog.domain.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private String userId;
    //private Long postId;
    private String content;
    private String createdAt;
    private Long commentId;

    public CommentResponseDto(Comment comment){
        this.userId=comment.getUser().getId();
        //this.postId=comment.getPost().getPost_id();
        this.commentId=comment.getComment_id();
        this.content=comment.getContent();
        this.createdAt=comment.getCreatedAt();
    }


}

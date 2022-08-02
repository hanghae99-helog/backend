package com.hanghae.helog.controller;

import com.hanghae.helog.dto.comment.CommentRequestDto;
import com.hanghae.helog.dto.comment.CommentResponseDto;
import com.hanghae.helog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class commentController {
    private final CommentService commentService;

    @PostMapping("api/comments/{post_id}")
    public ResponseEntity<?> saveComment(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl user,
                                         @RequestBody CommentRequestDto commentRequestDto) {
        return new ResponseEntity<>(commentService.saveComment(post_id, user, commentRequestDto), HttpStatus.valueOf(201));
    }

    @GetMapping("api/comments/{post_id}")
    public List<CommentResponseDto> commentList(@PathVariable Long post_id){
        return commentService.getCommentList(post_id);
    }

    @PutMapping("api/connebts/{comment_id}")
    public ResponseEntity<?> updateComment(@PathVariable Long comment_id,@AuthenticationPrincipal UserDetailsImpl user,@RequestBody CommentRequestDto commentRequestDto ){
        commentService.updataComment(commentId, commentRequestDto);
        return commentService.updateComment(comment_id, user, commentRequestDto);
    }

    @DeleteMapping("/api/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal UserDetailsImpl user, @PathVariable Long comment_id) {
        return commentService.deleteComment(comment_id, user);
    }
}

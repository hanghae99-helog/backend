package com.hanghae.helog.controller;

import com.hanghae.helog.domain.User;
import com.hanghae.helog.dto.comment.CommentRequestDto;
import com.hanghae.helog.dto.comment.CommentResponseDto;
import com.hanghae.helog.repository.CommentRepository;
import com.hanghae.helog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("api/comments/{post_id}")//댓글 리스트
    public List<CommentResponseDto> commentList(@PathVariable Long post_id){
        return commentService.getCommentList(post_id);
    }

    @PostMapping("api/comments/{post_id}")//댓글 작성
    public ResponseEntity<?> saveComment(@PathVariable Long post_id, @AuthenticationPrincipal User user, @RequestBody CommentRequestDto commentRequestDto) {

        commentService.saveComment(post_id, user, commentRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("api/comments/{comment_id}")//댓글 수정
    public ResponseEntity<?> updateComment(@PathVariable Long comment_id, @AuthenticationPrincipal User user, @RequestBody CommentRequestDto commentRequestDto ){
        commentService.updateComment(comment_id, user, commentRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/comments/{comment_id}")//댓글 삭제
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User user, @PathVariable Long comment_id) {
        return commentService.deleteComment(comment_id, user);
    }

}

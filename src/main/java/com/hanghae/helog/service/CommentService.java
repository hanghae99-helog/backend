package com.hanghae.helog.service;

import com.hanghae.helog.domain.Comment;
import com.hanghae.helog.domain.Post;
import com.hanghae.helog.domain.User;
import com.hanghae.helog.dto.comment.CommentRequestDto;
import com.hanghae.helog.dto.comment.CommentResponseDto;
import com.hanghae.helog.repository.CommentRepository;
import com.hanghae.helog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    //댓글 조회
    public List<CommentResponseDto> getCommentList(Long post_id) {
        Post post = postRepository.findById(post_id).get();

        List<Comment> comments = commentRepository.findAllByPostOrderByCreatedAt(post);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for(Comment comment : comments){
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }

        return commentResponseDtoList;
    }

    //댓글 작성
    public void saveComment(Long post_id, User user, CommentRequestDto commentRequestDto){
        Post post = postRepository.findById(post_id)
                .orElseThrow( () -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));

        Comment comment = new Comment(post, user, commentRequestDto);
        commentRepository.save(comment);
    }

    //댓글 수정
    public void updateComment(Long comment_id, User user, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(comment.getUser().getUser_id().equals(user.getUser_id())) {
            comment.update(commentRequestDto);
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }

    }

    //댓글 삭제
    public ResponseEntity<?> deleteComment(Long comment_id, User user){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("ID를 찾을 수 없습니다."));

        if(comment.getUser().getUser_id().equals(user.getUser_id())){
            commentRepository.deleteById(comment_id);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
    }
}

package com.hanghae.helog.service;

import com.hanghae.helog.domain.Comment;
import com.hanghae.helog.domain.Post;
import com.hanghae.helog.dto.comment.CommentRequestDto;
import com.hanghae.helog.dto.comment.CommentResponseDto;
import com.hanghae.helog.repository.CommentRepository;
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
    private final UserRepository userRepository;

    //댓글 조회
    public List<CommentResponseDto> getCommentList(Long post_id) {
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtDesc(post_id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment comment : comments){
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }
        return commentResponseDtoList;
    }

    //댓글 작성
    public CommentResponseDto saveComment(Long post_id, UserDetailsImpl user, CommentRequestDto commentRequestDto){
        Post post = postRepository.fidndById(post_id)
                .orElseThrow( () -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        Comment comment = new Comment(post, user, commentRequestDto);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    //댓글 수정
    public ResponseEntity<?> updateComment(Long comment_id, UserDetailsImpl user, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (comment.getUser().getUser_id().equals(user.getUser_id())) {
            comment.update(commentRequestDto);
            commentRepository.save(comment);
            return new ResponseEntity<>(HttpStatus.valueOf(204));
        } else {
            return new ResponseEntity<>(HttpStatus.valueOf(403));
        }

    }

    //댓글 삭제
    public ResponseEntity<?> deleteComment(Long comment_id, UserDetailsImpl user){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("ID를 찾을 수 없습니다."));

        if(comment.getUser().getUser_id().equals(user.getUser_id())){
            commentRepository.deleteById(comment_id);
            return new ResponseEntity<>(HttpStatus.valueOf(204));
        }else return new ResponseEntity<>(HttpStatus.valueOf(403));//???
    }

}

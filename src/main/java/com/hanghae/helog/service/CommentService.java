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

    private final CommentResponseDto commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<CommentResponseDto> getCommentList(Long post_id) {
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtDesc(post_id);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment comment : comments){
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }
        return commentResponseDtoList;
    }

    public CommentResponseDto saveComment(Long post_id, UserDetailsImpl user, CommentRequestDto commentRequestDto){
        Post post = postRepository.fidndByI(post_id)
                .orElseThrow( () -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        Comment comment = new Comment(post, user, commentRequestDto);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    private String updatComment(Long comment_id, UserDetailsImpl user, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (comment.getUser().getUser_id().equals(user.getUser_id())) {
            comment.setContent(commentRequestDto.getContent());
            commentRepository.save(comment);
            return "댓글이 수정 되었습니다.";
        } else {
            return "다른 사람의 댓글입니다.";
        }

    }

    public ResponseEntity<?> deleteComment(Long comment_id, UserDetailsImpl user){
        if(isWriter(comment_id,user)){
            CommentRepository.deletById(comment_id);
            return new ResponseEntity<>(HttpStatus.valueOf(204));
        }else return new ResponseEntity<>(HttpStatus.valueOf(403));//???
    }

    private boolean isWriter(Long comment_id, UserDetailsImpl user) {
        Comment comment = commentRepository.findById(comment_id).get();

        String writer = comment.getUser().getId();

        return equals(writer);
    }
}

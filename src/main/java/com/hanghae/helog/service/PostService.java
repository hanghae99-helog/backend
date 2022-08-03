package com.hanghae.helog.service;

import com.hanghae.helog.domain.Post;
import com.hanghae.helog.domain.User;
import com.hanghae.helog.dto.post.AllPostResponseDto;
import com.hanghae.helog.dto.post.PostCreateReqeustDto;
import com.hanghae.helog.dto.post.PostDetailResponseDto;
import com.hanghae.helog.dto.post.PostEditRequestDto;
import com.hanghae.helog.repository.CommentRepository;
import com.hanghae.helog.repository.PostRepository;
import com.hanghae.helog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    // 게시글 전체 조회
    public Slice<AllPostResponseDto> getAllPosts(Pageable pageable) {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        int start = 15 * (pageable.getPageNumber() - 1);
        int end = start + 15;

        List<AllPostResponseDto> allPostResponseDtoList = new ArrayList<>();

        for(int i=start; i<end; i++) {
            if(i < postList.size()) {
                int commentCount = commentRepository.countByPost(postList.get(i));

                AllPostResponseDto allPostResponseDto = new AllPostResponseDto(postList.get(i), commentCount);

                allPostResponseDtoList.add(allPostResponseDto);
            } else {
                break;
            }
        }

        boolean hasNext = false;

        if(postList.size() - (pageable.getPageNumber() * pageable.getPageSize()) > 0) {
            hasNext = true;
        }

        return new SliceImpl<>(allPostResponseDtoList, pageable, hasNext);
    }

    // 게시글 작성
    public void createPost(@AuthenticationPrincipal User user,
                           @RequestBody PostCreateReqeustDto postCreateReqeustDto) {

        // User 정보 가져오기
        Long user_id = user.getUser_id();
        User writer = userRepository.findById(user_id)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        // 게시글 입력
        Post post = new Post(writer, postCreateReqeustDto);

        // 게시글 DB 저장
        postRepository.save(post);
    }

    // 게시글 상세조회
    public PostDetailResponseDto getPostDetails(String url) {

        // 게시물 가져오기
        Post post = postRepository.findByUrl(url);

        // 댓글 개수 확인 및 가져오기
        int commentCount = commentRepository.countByPost(post);

        return new PostDetailResponseDto(post,commentCount);

    }

    // 게시글 수정
    public ResponseEntity<?> editPost(User user, Long post_id, PostEditRequestDto postEditRequestDto) {

        // 수정할 게시물 가져오기
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        // User의 수정 권한 여부 확인
        if (!post.getUser().getUser_id().equals(user.getUser_id())) {
            return new ResponseEntity<>(HttpStatus.valueOf(403));
        } else {
            // 게시글 수정
            post.editPost(postEditRequestDto);
            return new ResponseEntity<>(postRepository.save(post).getPost_id(), HttpStatus.valueOf(200));
        }
    }

    // 게시글 삭제
    public ResponseEntity<?> deletePost(User user, Long post_id) {

        // 삭제할 게시물 가져오기
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        // User의 삭제 권한 여부 확인
        if (!post.getUser().getUser_id().equals(user.getUser_id())) {
            return new ResponseEntity<>(HttpStatus.valueOf(403));
        } else {
            // 게시글 삭제
            postRepository.deleteById(post_id);
            return new ResponseEntity<>(HttpStatus.valueOf(200));
        }
    }

}
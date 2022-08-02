package com.hanghae.helog.service;

import com.hanghae.helog.domain.Post;
import com.hanghae.helog.domain.User;
import com.hanghae.helog.dto.post.AllPostResponseDto;
import com.hanghae.helog.dto.post.PostCreateReqeustDto;
import com.hanghae.helog.dto.post.PostDetailResponseDto;
import com.hanghae.helog.dto.post.PostEditRequestDto;
import com.hanghae.helog.repository.CommentRepository;
import com.hanghae.helog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    // 게시글 전체 조회
    public List<AllPostResponseDto> getAllPosts(int page, int size, String sortBy, boolean isAsc) {

        // 클라이언트는 1부터, 서버는 0부터 페이지가 시작되기 때문에 이를 맞춰주는 작업
        page = page -1;

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 전체 게시글 가져오기
        Page<Post> postList = postRepository.findAll(pageable);

        // Response 형식에 맞는 빈 배열 생성
        List<AllPostResponseDto> posts = new ArrayList<>();

        // postRepository에 저장된 전체 게시글을 반복문을 통해서 하나씩 꺼내오기
        for (Post post : postList) {

            // 각 게시글의 댓글 가져오기
            int commentCount = commentRepository.countByUrl(post.getUrl());

            // Response 형식에 맞게 내용 및 댓글 넣기
            AllPostResponseDto allPost = new AllPostResponseDto(post, commentCount);

            // posts 에 생성된 각각의 객체 추가
            posts.add(allPost);
        }

        return posts;
    }

    // 게시글 작성
    public void createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody PostCreateReqeustDto postCreateReqeustDto) {

        // User 정보 가져오기
        Long user_id = userDetails.getUser().getUser_id();
        User user = userRepository.findById(user_id)
                .orElseThrow(()-> new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        // 게시글 입력
        Post post = new Post(user, postCreateReqeustDto);

        // 게시글 DB 저장
        postRepository.save(post);
    }

    // 게시글 상세조회
    public PostDetailResponseDto getPostDetails(String url) {

        // 게시물 가져오기
        Post post = postRepository.findByUrl(url);

        // 댓글 개수 확인 및 가져오기
        int commentCount = commentRepository.countByUrl(url);

        return new PostDetailResponseDto(post,commentCount);

    }

    // 게시글 수정
    public ResponseEntity<?> editPost(UserDetailsImpl userDetails, Long post_id, PostEditRequestDto postEditRequestDto) {

        // 수정할 게시물 가져오기
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        // User의 수정 권한 여부 확인
        if (!post.GetUser().GetUser_id().equals(userDetails.getUser_id())) {
            return new ResponseEntity<>(HttpStatus.valueOf(403));
        } else {
            // 게시글 수정
            post.editPost(postEditRequestDto);
            return new ResponseEntity<>(postRepository.save(post).getPost_id(), HttpStatus.valueOf(200));
        }
    }

    // 게시글 삭제
    public ResponseEntity<?> deletePost(UserDetailsImpl userDetails, Long post_id) {

        // 삭제할 게시물 가져오기
        Post post = postRepository.findById(post_id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        // User의 삭제 권한 여부 확인
        if (!post.GetUser().GetUser_id().equals(userDetails.getUser_id())) {
            return new ResponseEntity<>(HttpStatus.valueOf(403));
        } else {
            // 게시글 삭제
            postRepository.deleteById(post_id);
            return new ResponseEntity<>(HttpStatus.valueOf(200));
        }
    }

}

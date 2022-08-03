package com.hanghae.helog.controller;

import com.hanghae.helog.dto.post.AllPostResponseDto;
import com.hanghae.helog.dto.post.PostCreateReqeustDto;
import com.hanghae.helog.dto.post.PostDetailResponseDto;
import com.hanghae.helog.dto.post.PostEditRequestDto;
import com.hanghae.helog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


    // 게시글 전체 조회

    @GetMapping("/api/list")
    public List<AllPostResponseDto> getAllPosts(@PageableDefault(page = 1, size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
                                                    Pageable pageable) {

        return postService.getAllPosts(pageable);

    }


//    @GetMapping("/api/list")
//    public List<AllPostResponseDto> getAllPosts(
//            @RequestParam("page") int page,
//            @RequestParam("size") int size,
//            @RequestParam("sortBy") String sortBy,
//            @RequestParam("isAsc") boolean isAsc
//    ) {
//        return postService.getAllPosts(page, size, sortBy, isAsc);
//    }


    // 게시글 작성
    @PostMapping("/api/posting")
    public void createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody PostCreateReqeustDto postCreateReqeustDto) {

        postService.createPost(userDetails, postCreateReqeustDto);
    }


    // 게시글 상세조회
    @GetMapping("/api/postiong/{url}")
    public PostDetailResponseDto getPostDetails(@RequestParam String url) {

        return postService.getPostDetails(url);
    }


    // 게시글 수정
    @PutMapping("/api/posting/{post_id}")
    public ResponseEntity<?> editPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long post_id,
                                      @RequestBody PostEditRequestDto postEditRequestDto) {

        return postService.editPost(userDetails, post_id, postEditRequestDto);
    }


    // 게시글 삭제
    @DeleteMapping("api/posting/{post_id}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @PathVariable Long post_id) {

        return postService.deletePost(userDetails, post_id);
    }

}
package com.hanghae.helog.controller;

import com.hanghae.helog.dto.post.PostCreateReqeustDto;
import com.hanghae.helog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;


    @PostMapping("/api/posting")
    public void createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody PostCreateReqeustDto postCreateReqeustDto) {

        postService.createPost(userDetails, postCreateReqeustDto);
    }

    @PutMapping("/api/posting/{postid}")
    public void editPost(@PathVariable Long postId,
                         @RequestBody EditPostRequestDto requestDto,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postService.editPost(postId, requestDto, userDetails);
    }

}
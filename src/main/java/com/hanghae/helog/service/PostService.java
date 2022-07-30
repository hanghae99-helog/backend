package com.hanghae.helog.service;

import com.hanghae.helog.domain.Post;
import com.hanghae.helog.domain.User;
import com.hanghae.helog.dto.post.PostCreateReqeustDto;
import com.hanghae.helog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;


    // 게시글 작성
    public void createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                           @RequestBody PostCreateReqeustDto postCreateReqeustDto) {

        // User 정보 가져오기
        User user = userRepository.findById(user_id)
                .orElseThrow(()->new IllegalArgumentException("유저 정보를 찾을 수 없습니다."));

        // 게시글 입력
        Post post = new Post(user, postCreateReqeustDto);

        // 게시글 DB 저장
        postRepository.save(post);
    }

    public void editPost(Long postId, EditPostRequestDto requestDto, UserDetailsImpl userDetails) {

        if(!user.getUser_id().equals(post.getUser().getUser_id())) {

        }

    }
}

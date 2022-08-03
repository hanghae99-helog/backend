package com.hanghae.helog.domain;

import com.hanghae.helog.dto.post.PostCreateReqeustDto;
import com.hanghae.helog.dto.post.PostEditRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "post")
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    private String title;

    private String subTitle;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String viewContent;

    @Column(columnDefinition = "TEXT")
    private String writingContent;

    private String thumbnail;

    private String url;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comment> comments;


    public Post(User user, PostCreateReqeustDto postCreateReqeustDto) {
        this.user = user;
        this.title = postCreateReqeustDto.getTitle();
        this.subTitle = postCreateReqeustDto.getSubTitle();
        this.viewContent = postCreateReqeustDto.getViewContent();
        this.writingContent = postCreateReqeustDto.getWritingContent();
        this.thumbnail = postCreateReqeustDto.getThumbnail();
        this.url = postCreateReqeustDto.getUrl();
    }

    public void editPost(PostEditRequestDto postEditRequestDto) {
        this.title = postEditRequestDto.getTitle();
        this.subTitle = postEditRequestDto.getSubTitle();
        this.viewContent = postEditRequestDto.getViewContent();
        this.writingContent = postEditRequestDto.getWritingContent();
        this.thumbnail = postEditRequestDto.getThumbnail();
        this.url = postEditRequestDto.getUrl();
    }
}
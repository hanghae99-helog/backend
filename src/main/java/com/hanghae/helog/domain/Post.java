package com.hanghae.helog.domain;

import com.hanghae.helog.dto.post.PostCreateReqeustDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String content;

    private String thumbnail;

    private String url;

    public Post(User user, PostCreateReqeustDto postCreateReqeustDto) {
        this.user = user;
        this.title = postCreateReqeustDto.getTitle();
        this.subTitle = postCreateReqeustDto.getSubTitle();
        this.content = postCreateReqeustDto.getContent();
        this.thumbnail = postCreateReqeustDto.getThumbnail();
        this.url = postCreateReqeustDto.getUrl();
    }

}

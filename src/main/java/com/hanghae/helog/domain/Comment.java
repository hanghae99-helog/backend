package com.hanghae.helog.domain;

import com.hanghae.helog.dto.comment.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    public Comment (Post post, User user, CommentRequestDto commentRequestDto){
        this.post=post;
        this.user=user;
        this.content=commentRequestDto.getContent();
    }

    public void update(CommentRequestDto requestDto){
        this.content = requestDto.getContent();
    }
}

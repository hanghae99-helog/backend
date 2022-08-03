package com.hanghae.helog.repository;

import com.hanghae.helog.domain.Comment;
import com.hanghae.helog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
       List<Comment> findAllByPostOrderByCreatedAt(Post post);
       int countByPost(Post post);
}

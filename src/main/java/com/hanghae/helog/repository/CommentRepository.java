package com.hanghae.helog.repository;

import com.hanghae.helog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
       List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long post_id);
       int countByUrl(String url);
}
